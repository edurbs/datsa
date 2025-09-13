package com.github.edurbs.datsa.api.controller;

import com.github.edurbs.datsa.domain.service.StatusOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders/{uuid}")
public class StatusOrderController {

    @Autowired
    private StatusOrderService statusOrderService;

    @PutMapping("/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirm(@PathVariable String uuid) {
        statusOrderService.confirm(uuid);
    }

    @PutMapping("/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable String uuid) {
        statusOrderService.cancel(uuid);
    }

    @PutMapping("/delivery")
    public void delivery(@PathVariable String uuid) {
        statusOrderService.delivery(uuid);
    }

}
