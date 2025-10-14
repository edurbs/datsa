package com.github.edurbs.datsa.api.v1.dto.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "paymentmethods")
@Setter
@Getter
public class PaymentMethodOutput extends RepresentationModel<PaymentMethodOutput> {

    private Long id;
    private String description;
}
