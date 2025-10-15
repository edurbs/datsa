package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurants")
@Setter
@Getter
public class RestaurantOutput extends RepresentationModel<RestaurantOutput> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Brazilian")
    private String name;

    @Schema(example = "1.95")
    private BigDecimal shippingFee;

    private KitchenOutput kitchen;

    @Schema(example = "true")
    private Boolean active;

    @Schema(example = "false")
    private Boolean open;

    private AddressOutput address;

}
