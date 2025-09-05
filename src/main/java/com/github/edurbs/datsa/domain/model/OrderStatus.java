package com.github.edurbs.datsa.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    CREATED("Created"),
    CONFIRMED("Confirmed"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled");

    private String description;

}
