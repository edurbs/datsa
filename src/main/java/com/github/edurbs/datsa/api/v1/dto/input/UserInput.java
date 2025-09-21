package com.github.edurbs.datsa.api.v1.dto.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput implements InputModel {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;
}
