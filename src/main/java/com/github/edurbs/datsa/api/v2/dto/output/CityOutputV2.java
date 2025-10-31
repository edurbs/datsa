package com.github.edurbs.datsa.api.v2.dto.output;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cities")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CityOutputV2 extends RepresentationModel<CityOutputV2> {

    private Long cityId;
    private String cityName;
    private Long stateId;
    private String stateName;
}
