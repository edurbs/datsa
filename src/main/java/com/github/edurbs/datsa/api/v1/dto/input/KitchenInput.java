package com.github.edurbs.datsa.api.v1.dto.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KitchenInput implements InputModel{

    @NotBlank
    private String name;

}
