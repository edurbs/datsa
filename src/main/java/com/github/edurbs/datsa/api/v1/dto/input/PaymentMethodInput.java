package com.github.edurbs.datsa.api.v1.dto.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodInput implements InputModel{

    @NotBlank
    private String description;
}
