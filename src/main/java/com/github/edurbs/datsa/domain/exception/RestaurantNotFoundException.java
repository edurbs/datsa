package com.github.edurbs.datsa.domain.exception;

public class RestaurantNotFoundException extends ModelNotFoundException {

    public RestaurantNotFoundException(Long id){
        super("Restaurant id %d not found.".formatted(id));
    }
}
