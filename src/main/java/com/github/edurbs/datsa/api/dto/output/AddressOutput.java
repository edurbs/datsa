package com.github.edurbs.datsa.api.dto.output;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.github.edurbs.datsa.domain.model.City;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressOutput {

    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private CitySummaryOutput city;
}

