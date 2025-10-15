package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurants")
@Getter
@Setter
public class RestaurantSummaryOutput extends RepresentationModel<RestaurantSummaryOutput> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Brazilian")
    private String name;

    @Schema(example = "1.95")
    private BigDecimal shippingFee;

    private KitchenOutput kitchen;
}
