package com.github.edurbs.datsa.api.v1.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordInput implements InputModel {

    @NotBlank
    private String oldPassword;

    @Size(min = 8)
    private String newPassword;
}
