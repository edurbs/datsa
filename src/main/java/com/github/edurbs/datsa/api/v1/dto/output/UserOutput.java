package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "users")
@Setter
@Getter
public class UserOutput extends RepresentationModel<UserOutput> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "SomeUserName")
    private String name;

    @Schema(example = "email@provider.com")
    private String email;
}
