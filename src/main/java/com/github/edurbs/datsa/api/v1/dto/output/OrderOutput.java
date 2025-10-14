package com.github.edurbs.datsa.api.v1.dto.output;

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
public class OrderOutput extends RepresentationModel<OrderOutput> {

    private String uuid;
    private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal totalAmount;
    private RestaurantSummaryOutput restaurant;
    private UserOutput user;
    private PaymentMethodOutput paymentMethod;
    private AddressOutput address;
    private String status;
    private OffsetDateTime creationDate;
    private OffsetDateTime confirmationDate;
    private OffsetDateTime cancellationDate;
    private OffsetDateTime deliveryDate;
    private List<OrderItemOutput> orderItems;

}
