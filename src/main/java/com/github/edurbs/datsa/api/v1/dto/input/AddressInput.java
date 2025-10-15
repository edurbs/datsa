package com.github.edurbs.datsa.api.v1.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressInput {

    @Schema(example = "37100-000")
    @NotBlank
    private String zipCode;

    @Schema(example = "Streen One")
    @NotBlank
    private String street;

    @Schema(example = "123")
    @NotBlank
    private String number;

    @Schema(example = "Apartment 3")
    private String complement;

    @Schema(example = "Small ville")
    @NotBlank
    private String neighborhood;

    @Valid
    @NotNull
    private CityIdInput city;
}
