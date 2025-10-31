package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cities")
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class CitySummaryOutput extends RepresentationModel<CitySummaryOutput> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "New York")
    private String name;

    @Schema(example = "NY")
    private String state;
}
