package com.github.edurbs.datsa.api.v1.dto.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateInput implements InputModel {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;
}
