package com.github.edurbs.datsa.api.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.dto.output.PaymentMethodOutput;
import com.github.edurbs.datsa.api.mapper.PaymentMethodMapper;
import com.github.edurbs.datsa.domain.service.RestaurantRegistryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurants/{restaurantId}/payment-methods")
@RequiredArgsConstructor
public class RestaurantPaymentMethodController {

    private final RestaurantRegistryService restaurantRegistryService;

    private final PaymentMethodMapper paymentMethodMapper;

    @GetMapping
    public Set<PaymentMethodOutput> listAll(@PathVariable Long restaurantId) {
        var restaurant = restaurantRegistryService.getById(restaurantId);

        return paymentMethodMapper.toOutputSet(restaurant.getPaymentMethods());
    }

    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId){
        restaurantRegistryService.disassociatePaymentMethod(restaurantId, paymentMethodId);
    }

    @PutMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId){
        restaurantRegistryService.associatePaymentMethod(restaurantId, paymentMethodId);
    }



}
