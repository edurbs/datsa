package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "groups")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GroupOutput extends RepresentationModel<GroupOutput> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Managers")
    private String name;

    @Schema(example = "Managers group")
    private String description;
}
