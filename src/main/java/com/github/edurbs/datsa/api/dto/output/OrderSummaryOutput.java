package com.github.edurbs.datsa.api.dto.output;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSummaryOutput implements OutputModel{

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal totalAmount;
    private RestaurantSummaryOutput restaurant;
    private UserOutput user;
    private String status;
    private OffsetDateTime creationDate;

}
