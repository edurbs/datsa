package com.github.edurbs.datsa.api.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantSummaryOutput implements OutputModel {
    private Long id;
    private String name;
}
