package com.github.edurbs.datsa.domain.event;

import com.github.edurbs.datsa.domain.model.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderConfirmedEvent {
    private Order order;
}
