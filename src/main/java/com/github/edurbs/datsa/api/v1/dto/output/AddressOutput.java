package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressOutput {

    @Schema(example = "37100-000")
    private String zipCode;

    @Schema(example = "Street one")
    private String street;

    @Schema(example = "31")
    private String number;

    @Schema(example = "Apartment 2")
    private String complement;

    @Schema(example = "Small Ville")
    private String neighborhood;

    private CitySummaryOutput city;
}

