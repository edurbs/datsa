package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "restaurants")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class RestaurantNameOutput extends RepresentationModel<RestaurantNameOutput> {

    @Schema(example = "1")
    public Long id;

    @Schema(example = "Brazilian")
    public String name;
}
