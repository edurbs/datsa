package com.github.edurbs.datsa.domain.exception;

public class ModelInUseException extends RuntimeException {

    public ModelInUseException(String message) {
        super(message);
    }

    public ModelInUseException() {
        super("Model in use.");
    }

}
