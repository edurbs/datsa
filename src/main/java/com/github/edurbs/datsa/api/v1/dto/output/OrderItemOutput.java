package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class OrderItemOutput extends RepresentationModel<OrderItemOutput> {

    @Schema(example = "1")
    private Long productId;

    @Schema(example = "Bread")
    private String productName;

    @Schema(example = "2")
    private Long quantity;

    @Schema(example = "1.95")
    private BigDecimal unitPrice;

    @Schema(example = "3.90")
    private BigDecimal totalPrice;

    @Schema(example = "Some note")
    private String note;
}
