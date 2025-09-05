package com.github.edurbs.datsa.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.domain.service.StatusOrderService;

@RestController
@RequestMapping(value="/orders/{orderId}")
public class StatusOrderController {

    @Autowired
    private StatusOrderService statusOrderService;

    @PutMapping("/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirm(@PathVariable Long orderId){
        statusOrderService.confirm(orderId);
    }
}
