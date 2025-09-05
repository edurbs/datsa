package com.github.edurbs.datsa.api.dto.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderInput implements InputModel {

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
