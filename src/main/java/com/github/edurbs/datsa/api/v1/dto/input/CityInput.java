package com.github.edurbs.datsa.api.v1.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityInput {

    @Schema(example = "Santos")
    @NotBlank
    private String name;

    @Schema(example = "1")
    @Valid
    @NotNull
    private StateIdInput state;

}
