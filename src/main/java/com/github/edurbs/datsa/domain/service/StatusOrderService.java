package com.github.edurbs.datsa.domain.service;

import com.github.edurbs.datsa.domain.model.Order;
import com.github.edurbs.datsa.domain.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StatusOrderService {

    private final OrderRegistryService orderRegistryService;
    private final OrderRepository orderRepository;

    public StatusOrderService(OrderRegistryService orderRegistryService, OrderRepository orderRepository) {
        this.orderRegistryService = orderRegistryService;
        this.orderRepository = orderRepository;
    }

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
        orderRepository.save(order);
    }

}
