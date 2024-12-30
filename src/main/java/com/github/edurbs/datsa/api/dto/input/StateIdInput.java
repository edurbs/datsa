package com.github.edurbs.datsa.api.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateIdInput implements InputModel{

    @NotNull
    private Long id;

}
