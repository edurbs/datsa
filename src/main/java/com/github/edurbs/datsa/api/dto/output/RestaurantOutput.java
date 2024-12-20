package com.github.edurbs.datsa.api.dto.output;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantOutput {

    private Long id;
    private String name;
    private BigDecimal shippingFee;
    private KitchenOutput kitchen;
    private Boolean active;
    private AddressOutput address;

}
