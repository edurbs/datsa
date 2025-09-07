package com.github.edurbs.datsa.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String GENERIC_USER_ERROR_MESSAGE = "An unexpected internal system error has occurred. Please try again and if the problem persists, contact your system administrator.";

    @Autowired
    private MessageSource messageSource;

    @Override
    protected @NonNull ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {
        Problem problem = handleValidationInternal(status, exception.getBindingResult());
        return handleExceptionInternal(exception, problem, headers, status, request);
    }

    private Problem handleValidationInternal(HttpStatus status, BindingResult bindingResult) {
        ProblemType problemType = ProblemType.VALIDATION_ERROR;
        String detail = "Validation error";
        List<Problem.Field> fields = bindingResult.getFieldErrors().stream()
                .map(error -> {
                    String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
                    return Problem.Field.builder()
                        .name(error.getField())
                        .userMessage(message)
                        .build();
                    })
                .toList();
        return createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .fields(fields)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.SYSTEM_ERROR;
        String detail = GENERIC_USER_ERROR_MESSAGE;
        Problem problem = createProblemBuilder(status, problemType, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected @NonNull ResponseEntity<Object> handleNoHandlerFoundException(
            @NonNull NoHandlerFoundException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String detail = "Resource %s does not exists.".formatted(ex.getRequestURL());
        Problem problem = createProblemBuilder(status, problemType, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected @NonNull ResponseEntity<Object> handleTypeMismatch(
            @NonNull TypeMismatchException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatchException(
                    methodArgumentTypeMismatchException, headers, status, request);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ProblemType problemType = ProblemType.INVALID_PARAMETER;
        String typeName = Optional.ofNullable(ex.getRequiredType())
                .map(Class::getSimpleName)
                .orElse("not informed");
        String detail = "The URL parameter '%s' received the invalid value '%s'. The compatible type is %s."
                .formatted(ex.getName(), ex.getValue(), typeName);
        Problem problem = createProblemBuilder(status, problemType, detail)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected @NonNull ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException invalidFormatException) {
            return handleInvalidFormat(invalidFormatException, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException propertyBindingException) {
            return handlePropertyBinding(propertyBindingException, headers, status, request);
        }
        ProblemType problemType = ProblemType.JSON_ERROR;
        String detail = "Json error. The request body is invalid.";
        Problem problem = createProblemBuilder(status, problemType, detail)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(
            PropertyBindingException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String path = joinPath(ex.getPath());
        ProblemType problemType = ProblemType.JSON_ERROR;
        String detail = "The property '%s' does not exists.".formatted(path);
        Problem problem = createProblemBuilder(status, problemType, detail)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(
            InvalidFormatException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String path = joinPath(ex.getPath());
        ProblemType problemType = ProblemType.JSON_ERROR;
        String detail = "The property '%s' received the invalid value '%s'. The compatible type is %s.".formatted(path,
                ex.getValue(), ex.getTargetType().getSimpleName());
        Problem problem = createProblemBuilder(status, problemType, detail)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<?> handleModelNotFound(ModelNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ModelInUseException.class)
    public ResponseEntity<?> handleModelInUse(ModelInUseException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTITY_IN_USE;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ModelValidationException.class)
    public ResponseEntity<?> handleModelValidation(ModelValidationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.VALIDATION_ERROR;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected @NonNull ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex,
            @Nullable Object body,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {
        if (body == null) {
            body = Problem.builder()
                    .timestamp(null)
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(GENERIC_USER_ERROR_MESSAGE)
                    .build();
        } else if (body instanceof String string) {
            body = Problem.builder()
                    .timestamp(null)
                    .title(string)
                    .status(status.value())
                    .userMessage(GENERIC_USER_ERROR_MESSAGE)
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
        return Problem.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .userMessage(GENERIC_USER_ERROR_MESSAGE)
                .detail(detail);
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    @Override
    protected @NonNull ResponseEntity<Object> handleBindException(@NonNull BindException exception, @NonNull HttpHeaders headers, @NonNull HttpStatus status,
            @NonNull WebRequest request) {
        Problem problem = handleValidationInternal(status, exception.getBindingResult());
        return handleExceptionInternal(exception, problem, headers, status, request);

    }
}
