package com.github.edurbs.datsa.api.dto.output;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CitySummaryOutput implements OutputModel {

    private Long id;
    private String name;
    private String state;
}
