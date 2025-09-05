package com.github.edurbs.datsa.domain.model;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED("Created"),
    CONFIRMED("Confirmed", CREATED),
    DELIVERED("Delivered", CONFIRMED),
    CANCELLED("Cancelled", CREATED, CONFIRMED);

    private String description;
    private List<OrderStatus> previousStatus;

    OrderStatus(String description, OrderStatus... previousStatus){
        this.description = description;
        this.previousStatus =  Arrays.asList(previousStatus);
    }

    public boolean cantChangeTo(OrderStatus newStatus){
        return !newStatus.previousStatus.contains(this);
    }

}
