package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "kitchens")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class KitchenOutput extends RepresentationModel<KitchenOutput> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Brazilian")
    private String name;
}
