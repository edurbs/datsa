package com.github.edurbs.datsa.api.v2.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityInputV2 {

    @NotBlank
    private String name;

    @NotNull
    private Long stateId;

}
