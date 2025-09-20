package com.github.edurbs.datsa.api.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.LinksAdder;
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

    private final LinksAdder linksAdder;

    @GetMapping
    public CollectionModel<PaymentMethodOutput> listAll(@PathVariable Long restaurantId) {
        var restaurant = restaurantRegistryService.getById(restaurantId);
        CollectionModel<PaymentMethodOutput> collectionModel = paymentMethodMapper.toCollectionModel(restaurant.getPaymentMethods())
            .removeLinks()
            .add(linksAdder.toRestaurantPaymentMethods(restaurantId))
            .add(linksAdder.associatePaymentMethod(restaurantId, "associate"));

        collectionModel.getContent().forEach(paymentMethod -> {
            paymentMethod.add(linksAdder.disassociatePaymentMethod(restaurantId, paymentMethod.getId(), "disassociate"));
        });
        return collectionModel;

    }

    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> disassociate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId){
        restaurantRegistryService.disassociatePaymentMethod(restaurantId, paymentMethodId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId){
        restaurantRegistryService.associatePaymentMethod(restaurantId, paymentMethodId);
        return ResponseEntity.noContent().build();
    }



}
