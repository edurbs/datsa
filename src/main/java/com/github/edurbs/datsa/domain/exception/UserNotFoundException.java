package com.github.edurbs.datsa.domain.exception;

public class UserNotFoundException extends ModelNotFoundException {

    public UserNotFoundException(Long id){
        super("User id %s does not exists".formatted(id));
    }
}
