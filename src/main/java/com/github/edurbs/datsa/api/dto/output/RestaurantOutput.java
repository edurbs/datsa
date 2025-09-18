package com.github.edurbs.datsa.api.dto.output;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurants")
@Setter
@Getter
public class RestaurantOutput extends RepresentationModel<RestaurantOutput> implements OutputModel {

    private Long id;
    private String name;
    private BigDecimal shippingFee;
    private KitchenOutput kitchen;
    private Boolean active;
    private Boolean open;
    private AddressOutput address;

}
