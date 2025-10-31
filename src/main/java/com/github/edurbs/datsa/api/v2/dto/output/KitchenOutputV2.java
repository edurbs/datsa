package com.github.edurbs.datsa.api.v2.dto.output;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "kitchens")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class KitchenOutputV2 extends RepresentationModel<KitchenOutputV2>  {

    private Long kitchenId;
    private String kitchenName;
}
