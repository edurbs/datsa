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
        if(!order.getStatus().equals(OrderStatus.CREATED)){
            throw new ModelValidationException("Order %d status can't but altered from %s to %s.".formatted(order.getId(), order.getStatus().getDescription(), OrderStatus.CONFIRMED.getDescription()));
        }
        order.setStatus(OrderStatus.CONFIRMED);
        order.setConfirmationDate(OffsetDateTime.now());
    }
}
