package com.github.edurbs.datsa.core.validation;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private String[] contentType;

	@Override
	public void initialize(FileContentType constraintAnnotation) {
		contentType = constraintAnnotation.allowed();
	}

	@Override
	public boolean isValid(@Nullable MultipartFile value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(contentType).contains(value.getContentType());

	}

}
