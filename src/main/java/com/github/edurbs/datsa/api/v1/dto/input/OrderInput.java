package com.github.edurbs.datsa.api.v1.dto.input;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class OrderInput {

    @Valid
    @NotNull
    private RestaurantIdInput restaurant;

    @Valid
    @NotNull
    private AddressInput address;

    @Valid
    @NotNull
    private PaymentMethodIdInput paymentMethod;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<OrderItemInput> items;
}
