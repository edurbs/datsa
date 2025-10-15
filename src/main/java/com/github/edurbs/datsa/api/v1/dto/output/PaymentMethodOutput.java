package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "paymentmethods")
@Setter
@Getter
public class PaymentMethodOutput extends RepresentationModel<PaymentMethodOutput> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Money")
    private String description;
}
