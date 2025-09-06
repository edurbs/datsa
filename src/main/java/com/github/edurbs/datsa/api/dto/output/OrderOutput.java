package com.github.edurbs.datsa.api.dto.output;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderOutput implements OutputModel{

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
