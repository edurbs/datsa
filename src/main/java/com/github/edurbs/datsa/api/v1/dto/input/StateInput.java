package com.github.edurbs.datsa.api.v1.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StateInput {

    @Schema(example = "Santos")
    @NotBlank
    private String name;
}
