package com.github.edurbs.datsa.api.v1.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class UserInput {

    @Schema(example = "Steve")
    @NotBlank
    private String name;

    @Schema(example = "email@provider.com")
    @Email
    @NotBlank
    private String email;

    @Schema(example = "goodPassword")
    @NotNull
    @Size(min = 8)
    private String password;
}
