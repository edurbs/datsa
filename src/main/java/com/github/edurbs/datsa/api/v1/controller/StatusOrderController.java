package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.openapi.controller.StatusOrderControllerOpenApi;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.domain.service.StatusOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/orders/{uuid}")
public class StatusOrderController implements StatusOrderControllerOpenApi {

    @Autowired
    private StatusOrderService statusOrderService;

    @CheckSecurity.Orders.CanManageOrders
    @PutMapping("/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> confirm(@PathVariable String uuid) {
        statusOrderService.confirm(uuid);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Orders.CanManageOrders
    @PutMapping("/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> cancel(@PathVariable String uuid) {
        statusOrderService.cancel(uuid);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Orders.CanManageOrders
    @PutMapping("/delivery")
    @Override
    public ResponseEntity<Void> delivery(@PathVariable String uuid) {
        statusOrderService.delivery(uuid);
        return ResponseEntity.noContent().build();
    }

}
