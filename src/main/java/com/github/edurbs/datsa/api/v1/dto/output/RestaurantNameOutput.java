package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurants")
@Getter
@Setter
public class RestaurantNameOutput extends RepresentationModel<RestaurantNameOutput> {

    @Schema(example = "1")
    public Long id;

    @Schema(example = "Brazilian")
    public String name;
}
