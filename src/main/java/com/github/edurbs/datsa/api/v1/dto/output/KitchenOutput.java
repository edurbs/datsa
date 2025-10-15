package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "kitchens")
@Getter
@Setter
public class KitchenOutput extends RepresentationModel<KitchenOutput> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Brazilian")
    private String name;
}
