package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Relation(collectionRelation = "orders")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderSummaryOutput extends RepresentationModel<OrderSummaryOutput> {

    @Schema(example = "eb1b17c8-5f0d-464e-8384-bd38f60b9784")
    String uuid;

    @Schema(example = "29.95")
    BigDecimal subtotal;

    @Schema(example = "1.95")
    BigDecimal shippingFee;

    @Schema(example = "31.90")
    BigDecimal totalAmount;

    RestaurantSummaryOutput restaurant;

    @Schema(example = "someusername")
    String userName;

    @Schema(example = "29.99")
    String status;

    @Schema(example = "2025-10-15T13:48:55Z")
    OffsetDateTime creationDate;

}
