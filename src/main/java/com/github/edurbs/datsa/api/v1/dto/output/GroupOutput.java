package com.github.edurbs.datsa.api.v1.dto.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "groups")
@Getter
@Setter
public class GroupOutput extends RepresentationModel<GroupOutput> {

    private Long id;
    private String name;
    private String description;
}
