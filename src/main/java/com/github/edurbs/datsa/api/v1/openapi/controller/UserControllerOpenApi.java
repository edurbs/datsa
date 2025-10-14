package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.UserInput;
import com.github.edurbs.datsa.api.v1.dto.input.UserPasswordInput;
import com.github.edurbs.datsa.api.v1.dto.input.UserUpdateInput;
import com.github.edurbs.datsa.api.v1.dto.output.UserOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@SecurityRequirement(name="security_auth")
public interface UserControllerOpenApi {
    CollectionModel<UserOutput> getAll();

    UserOutput getOne(@PathVariable Long id);

    UserOutput add(@RequestBody @Valid UserInput userInput);

    UserOutput alter(@PathVariable Long id, @RequestBody @Valid UserUpdateInput userUpdateInput);

    void remove(@PathVariable Long id);

    UserOutput alterPassword(@PathVariable Long userId, @RequestBody @Valid UserPasswordInput userPasswordInput);
}
