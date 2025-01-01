package com.github.edurbs.datsa.api.dto.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateInput {

    @NotNull
    private String name;

    @Email
    private String email;
}
