package com.github.edurbs.datsa.api.v1.dto.output;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurants")
@Getter
@Setter
public class RestaurantSummaryOutput extends RepresentationModel<RestaurantSummaryOutput> implements OutputModel {
    private Long id;
    private String name;
    private BigDecimal shippingFee;
    private KitchenOutput kitchen;
}
