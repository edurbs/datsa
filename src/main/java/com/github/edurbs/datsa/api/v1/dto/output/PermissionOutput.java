package com.github.edurbs.datsa.api.v1.dto.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "permissions")
@Getter
@Setter
public class PermissionOutput extends RepresentationModel<PermissionOutput> implements OutputModel {

    private Long id;
    private String name;
    private String description;
}
