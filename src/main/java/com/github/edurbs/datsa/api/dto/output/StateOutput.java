package com.github.edurbs.datsa.api.dto.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "states")
@Getter
@Setter
public class StateOutput extends RepresentationModel<StateOutput> implements OutputModel {

    private Long id;
    private String name;

}
