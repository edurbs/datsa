package com.github.edurbs.datsa.api.v1.dto.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurants")
@Getter
@Setter
public class RestaurantNameOutput extends RepresentationModel<RestaurantNameOutput> {
    public Long id;
    public String name;
}
