package com.github.edurbs.datsa.api.v2.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class KitchenInputV2 {

    @NotBlank
    private String name;

}
