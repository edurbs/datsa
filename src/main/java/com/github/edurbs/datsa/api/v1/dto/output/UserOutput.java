package com.github.edurbs.datsa.api.v1.dto.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "users")
@Setter
@Getter
public class UserOutput extends RepresentationModel<UserOutput> implements OutputModel {

    private Long id;
    private String name;
    private String email;
}
