package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.output.PaymentMethodOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@SecurityRequirement(name="security_auth")
public interface RestaurantPaymentMethodControllerOpenApi {
    CollectionModel<PaymentMethodOutput> listAll(@PathVariable Long restaurantId);

    ResponseEntity<Void> disassociate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId);

    ResponseEntity<Void> associate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId);
}
