package com.github.edurbs.datsa.api.dto.output;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOutput implements OutputModel {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean active;
}
