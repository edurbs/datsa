package com.github.edurbs.datsa.api.v1.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GroupInput {

    @NotNull
    private String name;

    @NotNull
    private String description;
}
