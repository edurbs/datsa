package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Relation(collectionRelation = "orders")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class OrderOutput extends RepresentationModel<OrderOutput> {

    @Schema(example = "eb1b17c8-5f0d-464e-8384-bd38f60b9784")
    private String uuid;

    @Schema(example = "29.95")
    private BigDecimal subtotal;

    @Schema(example = "1.95")
    private BigDecimal shippingFee;

    @Schema(example = "31.90")
    private BigDecimal totalAmount;

    private RestaurantSummaryOutput restaurant;

    private UserOutput user;

    private PaymentMethodOutput paymentMethod;

    private AddressOutput address;

    @Schema(example = "Created")
    private String status;

    @Schema(example = "2025-10-15T13:48:55Z")
    private OffsetDateTime creationDate;

    @Schema(example = "2025-10-15T13:48:55Z")
    private OffsetDateTime confirmationDate;

    @Schema(example = "2025-10-15T13:48:55Z")
    private OffsetDateTime cancellationDate;

    @Schema(example = "2025-10-15T13:48:55Z")
    private OffsetDateTime deliveryDate;

    private List<OrderItemOutput> orderItems;

}
