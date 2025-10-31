package com.github.edurbs.datsa.api.v1.dto.output;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "products")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ProductOutput extends RepresentationModel<ProductOutput> {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
}
