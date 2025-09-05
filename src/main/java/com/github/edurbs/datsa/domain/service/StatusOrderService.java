package com.github.edurbs.datsa.domain.service;

import java.time.OffsetDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.model.Order;
import com.github.edurbs.datsa.domain.model.OrderStatus;

@Service
public class StatusOrderService {

    @Autowired
    OrderRegistryService orderRegistryService;

    @Transactional
    public void confirm(Long orderId){
        Order order = orderRegistryService.getById(orderId);
        checkStatus(order, OrderStatus.CREATED, OrderStatus.CONFIRMED);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setConfirmationDate(OffsetDateTime.now());
    }

    @Transactional
    public void delivery(Long orderId){
        Order order = orderRegistryService.getById(orderId);
        checkStatus(order, OrderStatus.CONFIRMED, OrderStatus.DELIVERED);
        order.setStatus(OrderStatus.DELIVERED);
        order.setDeliveryDate(OffsetDateTime.now());
    }

    @Transactional
    public void cancel(Long orderId){
        Order order = orderRegistryService.getById(orderId);
        checkStatus(order, OrderStatus.CREATED, OrderStatus.CANCELLED);
        order.setStatus(OrderStatus.CANCELLED);
        order.setCancellationDate(OffsetDateTime.now());
    }

    private void checkStatus(Order order, OrderStatus orderEquals, OrderStatus orderTo) {
        if(!order.getStatus().equals(orderEquals)){
            throw new ModelValidationException("Status of the order %d can't be changed from %s to %s".formatted(order.getId(), order.getStatus().getDescription(), orderTo.getDescription()));
        }
    }
}
