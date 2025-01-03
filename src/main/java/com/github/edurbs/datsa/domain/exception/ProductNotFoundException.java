package com.github.edurbs.datsa.domain.exception;

public class ProductNotFoundException extends ModelNotFoundException {

    public ProductNotFoundException(Long id){
        super("Product id %d not found".formatted(id));
    }
}
