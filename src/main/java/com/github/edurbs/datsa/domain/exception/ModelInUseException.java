package com.github.edurbs.datsa.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ModelInUseException extends RuntimeException {

    public ModelInUseException(String message) {
        super(message);
    }

    public ModelInUseException() {
        super("Model in use.");
    }

}
