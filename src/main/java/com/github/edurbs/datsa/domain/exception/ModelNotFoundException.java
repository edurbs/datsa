package com.github.edurbs.datsa.domain.exception;

public class ModelNotFoundException extends RuntimeException {

    public ModelNotFoundException(String message){
        super(message);
    }

    public ModelNotFoundException(){
        super("Kitchen not found.");
    }
}
