package com.github.edurbs.datsa.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.edurbs.datsa.domain.model.Order;

@Service
public class StatusOrderService {

    @Autowired
    OrderRegistryService orderRegistryService;

    @Transactional
    public void confirm(Long orderId){
        Order order = orderRegistryService.getById(orderId);
        order.confirm();
    }

    @Transactional
    public void delivery(Long orderId){
        Order order = orderRegistryService.getById(orderId);
        order.delivery();
    }

    @Transactional
    public void cancel(Long orderId){
        Order order = orderRegistryService.getById(orderId);
        order.cancel();
    }

}
