package com.github.edurbs.datsa.api.dto.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "paymentmethods")
@Setter
@Getter
public class PaymentMethodOutput extends RepresentationModel<PaymentMethodOutput> implements OutputModel {

    private Long id;
    private String description;
}
