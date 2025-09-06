package com.github.edurbs.datsa.api.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.dto.input.OrderInput;
import com.github.edurbs.datsa.api.dto.output.OrderOutput;
import com.github.edurbs.datsa.api.dto.output.OrderSummaryOutput;
import com.github.edurbs.datsa.api.mapper.OrderMapper;
import com.github.edurbs.datsa.api.mapper.OrderSummaryMapper;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.model.Order;
import com.github.edurbs.datsa.domain.model.User;
import com.github.edurbs.datsa.domain.service.OrderRegistryService;




@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderRegistryService orderRegistryService;

    @Autowired
    OrderSummaryMapper orderSummaryMapper;

    @Autowired
    OrderMapper orderMapper;

    @GetMapping("/{uuid}")
    public OrderOutput getById(@PathVariable String uuid) {
        return orderMapper.toOutput(orderRegistryService.getById(uuid));
    }

    @GetMapping()
    public Collection<OrderSummaryOutput> listAll() {
        return orderSummaryMapper.toOutputList(orderRegistryService.getAll());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public OrderOutput newOrder(@Valid @RequestBody OrderInput orderInput) {
        try {
            Order newOrder = new Order();
            orderMapper.copyToDomain(orderInput, newOrder);
            newOrder.setUser(new User());
            newOrder.getUser().setId(1L);
            return orderMapper.toOutput(orderRegistryService.newOrder(newOrder));

        } catch (Exception e) {
            throw new ModelValidationException(e.getMessage());
        }
    }


}