package com.github.edurbs.datsa.api.dto.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput {

    @NotNull
    private String name;

    @Email
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;
}
