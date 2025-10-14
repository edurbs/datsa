package com.github.edurbs.datsa.api.v1.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserPasswordInput  {

    @NotBlank
    private String oldPassword;

    @Size(min = 8)
    private String newPassword;
}
