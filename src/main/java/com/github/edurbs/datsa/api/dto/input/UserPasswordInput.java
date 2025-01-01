package com.github.edurbs.datsa.api.dto.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordInput implements InputModel {

    @NotNull
    private String oldPassword;

    @Size(min = 8)
    private String newPassword;
}
