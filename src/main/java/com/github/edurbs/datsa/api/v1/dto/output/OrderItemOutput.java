package com.github.edurbs.datsa.api.v1.dto.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemOutput extends RepresentationModel<OrderItemOutput> {
    private Long productId;
    private String productName;
    private Long quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String note;
}
