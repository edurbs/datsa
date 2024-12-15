package com.github.edurbs.datsa.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ModelValidationException extends RuntimeException {

    public ModelValidationException(String message) {
        super(message);
    }

    public ModelValidationException() {
        super("Validation error.");
    }

}
