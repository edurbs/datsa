package com.github.edurbs.datsa.domain.exception;

public class ProductPhotoNotFoundException extends ModelNotFoundException {
    public ProductPhotoNotFoundException(String message) {
        super(message);
    }

    public ProductPhotoNotFoundException(Long productId) {
        super("Photo not found of product %s".formatted(productId));
    }
}
