package com.github.edurbs.datsa.api.dto.output;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderSummaryOutput implements OutputModel{

    String uuid;
    BigDecimal subtotal;
    BigDecimal shippingFee;
    BigDecimal totalAmount;
    RestaurantSummaryOutput restaurant;
    String userName;
    String status;
    OffsetDateTime creationDate;

}
