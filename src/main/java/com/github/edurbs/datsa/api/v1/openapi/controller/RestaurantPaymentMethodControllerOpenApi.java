package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.output.PaymentMethodOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name="security_auth")
@Tag(name = "Restaurants")
public interface RestaurantPaymentMethodControllerOpenApi {

    @Operation(summary = "List all payment methods of a restaurant")
    CollectionModel<PaymentMethodOutput> listAll(
            @Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId);

    @Operation(summary = "Disassociate a payment methods from a restaurant")
    ResponseEntity<Void> disassociate(
            @Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId,
            @Parameter(description = "Payment Method ID", example = "1", required = true) Long paymentMethodId);

    @Operation(summary = "Associate a payment methods to a restaurant")
    ResponseEntity<Void> associate(
            @Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId,
            @Parameter(description = "Payment Method ID", example = "1", required = true) Long paymentMethodId);
}
