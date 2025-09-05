package com.github.edurbs.datsa.api.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.dto.output.OrderOutput;
import com.github.edurbs.datsa.api.mapper.OrderMapper;
import com.github.edurbs.datsa.domain.service.OrderRegistryService;



@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderRegistryService orderRegistryService;

    @Autowired
    OrderMapper orderMapper;

    @GetMapping("/{id}")
    public OrderOutput getById(@PathVariable Long id) {
        return orderMapper.toOutput(orderRegistryService.getById(id));
    }

    @GetMapping()
    public Collection<OrderOutput> listAll() {
        return orderMapper.toOutputList(orderRegistryService.getAll());
    }

}