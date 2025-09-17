package com.github.edurbs.datsa.api.dto.output;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemOutput extends RepresentationModel<OrderItemOutput> implements OutputModel {
    private Long productId;
    private String productName;
    private Long quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String note;
}
