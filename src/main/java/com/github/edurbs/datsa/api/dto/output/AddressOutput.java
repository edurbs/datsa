package com.github.edurbs.datsa.api.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressOutput implements OutputModel {

    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private CitySummaryOutput city;
}

