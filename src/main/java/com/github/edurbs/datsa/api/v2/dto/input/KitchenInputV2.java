package com.github.edurbs.datsa.api.v2.dto.input;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class KitchenInputV2 {

    @NotBlank
    private String name;

}
