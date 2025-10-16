package com.github.edurbs.datsa.api.v1.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "City input representation")
@Getter
@Setter
public class CityInput {

    @Schema(example = "Santos")
    @NotBlank
    private String name;

    @Valid
    @NotNull
    private StateIdInput state;

}
