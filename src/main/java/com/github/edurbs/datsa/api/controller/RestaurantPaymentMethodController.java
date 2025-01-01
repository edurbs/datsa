package com.github.edurbs.datsa.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.dto.output.PaymentMethodOutput;
import com.github.edurbs.datsa.api.mapper.PaymentMethodMapper;
import com.github.edurbs.datsa.domain.service.RestaurantRegistryService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/payment-methods")
public class RestaurantPaymentMethodController {

    @Autowired
    private RestaurantRegistryService restaurantRegistryService;

    @Autowired
    private PaymentMethodMapper paymentMethodMapper;

    @GetMapping
    public List<PaymentMethodOutput> listAll(@PathVariable Long restaurantId) {
        var restaurant = restaurantRegistryService.getById(restaurantId);

        return paymentMethodMapper.toOutputList(restaurant.getPaymentMethods());
    }

    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId){
        restaurantRegistryService.disassociatePaymentMethod(restaurantId, paymentMethodId);
    }

}
