package com.github.edurbs.datsa.api.v1.dto.output;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "products")
@Getter
@Setter
public class ProductOutput extends RepresentationModel<ProductOutput> implements OutputModel {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
}
