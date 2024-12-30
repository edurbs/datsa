package com.github.edurbs.datsa.api.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupInput implements InputModel {

    @NotNull
    private String name;

    @NotNull
    private String description;
}
