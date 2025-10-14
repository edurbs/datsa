package com.github.edurbs.datsa.api.v1.dto.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurants")
@Getter
@Setter
public class RestaurantSummaryOutput extends RepresentationModel<RestaurantSummaryOutput> {
    private Long id;
    private String name;
    private BigDecimal shippingFee;
    private KitchenOutput kitchen;
}
