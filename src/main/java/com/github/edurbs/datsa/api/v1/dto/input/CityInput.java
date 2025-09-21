package com.github.edurbs.datsa.api.v1.dto.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityInput implements InputModel {

    @NotBlank
    private String name;

    @Valid
    @NotNull
    private StateIdInput state;

}
