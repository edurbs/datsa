package com.github.edurbs.datsa.api.v1.dto.input;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class PermissionInput {

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
