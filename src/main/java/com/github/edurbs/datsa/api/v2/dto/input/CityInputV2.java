package com.github.edurbs.datsa.api.v2.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityInputV2 {

    @NotBlank
    private String name;

    @NotNull
    private Long stateId;

//    @Valid
//    @NotNull
//    private StateIdInput state;

}
