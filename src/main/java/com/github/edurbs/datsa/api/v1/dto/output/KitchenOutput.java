package com.github.edurbs.datsa.api.v1.dto.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "kitchens")
@Getter
@Setter
public class KitchenOutput extends RepresentationModel<KitchenOutput> implements OutputModel {

    private Long id;
    private String name;
}
