package com.github.edurbs.datsa.domain.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.Order;
import com.github.edurbs.datsa.domain.repository.OrderRepository;

@Service
public class OrderRegistryService {

    @Autowired
    private OrderRepository orderRepository;

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(()-> new ModelNotFoundException("Order id %s does not exists".formatted(id)));
    }

    public Collection<Order> getAll() {
        return orderRepository.findAll();
    }

}
