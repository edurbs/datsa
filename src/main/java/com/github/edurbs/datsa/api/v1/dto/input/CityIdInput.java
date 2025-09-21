package com.github.edurbs.datsa.api.v1.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityIdInput implements InputModel {

    @NotNull
    private Long id;

}

