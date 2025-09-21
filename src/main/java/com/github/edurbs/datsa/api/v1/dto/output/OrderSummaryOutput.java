package com.github.edurbs.datsa.api.v1.dto.output;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Relation(collectionRelation = "orders")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderSummaryOutput extends RepresentationModel<OrderSummaryOutput> implements OutputModel{

    String uuid;
    BigDecimal subtotal;
    BigDecimal shippingFee;
    BigDecimal totalAmount;
    RestaurantSummaryOutput restaurant;
    String userName;
    String status;
    OffsetDateTime creationDate;

}
