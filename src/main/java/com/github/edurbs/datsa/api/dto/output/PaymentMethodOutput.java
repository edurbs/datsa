package com.github.edurbs.datsa.api.dto.output;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentMethodOutput {

    private Long id;
    private String description;
}
