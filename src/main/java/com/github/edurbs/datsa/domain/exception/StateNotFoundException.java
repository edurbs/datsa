package com.github.edurbs.datsa.domain.exception;

public class StateNotFoundException extends ModelNotFoundException {

    public StateNotFoundException(String msg){
        super(msg);
    }

    public StateNotFoundException(Long id){
        super("State id %d does not exist".formatted(id));
    }
}
