package com.github.edurbs.datsa.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.edurbs.datsa.domain.model.Order;
import com.github.edurbs.datsa.domain.repository.OrderRepository;

@Service
public class StatusOrderService {

    @Autowired
    private OrderRegistryService orderRegistryService;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void confirm(String uuid){
        Order order = orderRegistryService.getById(uuid);
        order.confirm();
        orderRepository.save(order); // must save to event (orderConfirmedEvent) be sent
    }

    @Transactional
    public void delivery(String uuid){
        Order order = orderRegistryService.getById(uuid);
        order.delivery();
    }

    @Transactional
    public void cancel(String uuid){
        Order order = orderRegistryService.getById(uuid);
        order.cancel();
    }

}
