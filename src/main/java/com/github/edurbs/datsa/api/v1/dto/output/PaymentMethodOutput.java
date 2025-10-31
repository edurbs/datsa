package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "paymentmethods")
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class PaymentMethodOutput extends RepresentationModel<PaymentMethodOutput> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Money")
    private String description;
}
