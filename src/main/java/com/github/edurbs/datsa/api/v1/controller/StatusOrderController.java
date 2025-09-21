package com.github.edurbs.datsa.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.domain.service.StatusOrderService;

@RestController
@RequestMapping(value = "/v1/orders/{uuid}")
public class StatusOrderController {

    @Autowired
    private StatusOrderService statusOrderService;

    @PutMapping("/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirm(@PathVariable String uuid) {
        statusOrderService.confirm(uuid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancel(@PathVariable String uuid) {
        statusOrderService.cancel(uuid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/delivery")
    public ResponseEntity<Void> delivery(@PathVariable String uuid) {
        statusOrderService.delivery(uuid);
        return ResponseEntity.noContent().build();
    }

}
