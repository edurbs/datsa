package com.github.edurbs.datsa.api.dto.output;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityOutput extends RepresentationModel<CityOutput> implements OutputModel  {

    private Long id;
    private String name;
    private StateOutput state;
}
