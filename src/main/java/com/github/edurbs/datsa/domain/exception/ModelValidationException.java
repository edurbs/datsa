package com.github.edurbs.datsa.domain.exception;

import org.springframework.ui.Model;

public class ModelValidationException extends RuntimeException {

    public ModelValidationException(String message) {
        super(message);
    }

    public ModelValidationException() {
        super("Validation error.");
    }

}