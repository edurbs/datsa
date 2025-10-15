package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.UserInput;
import com.github.edurbs.datsa.api.v1.dto.input.UserPasswordInput;
import com.github.edurbs.datsa.api.v1.dto.input.UserUpdateInput;
import com.github.edurbs.datsa.api.v1.dto.output.UserOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name="security_auth")
@Tag(name = "Users", description = "Users registry")
public interface UserControllerOpenApi {

    @Operation(summary = "List users")
    CollectionModel<UserOutput> getAll();

    @Operation(summary = "Get an user")
    UserOutput getOne(@Parameter(description = "User ID", example = "1", required = true) Long userId);

    @Operation(summary = "Add an user")
    UserOutput add(@RequestBody(description = "New user representation", required = true) UserInput userInput);

    @Operation(summary = "Update an user")
    UserOutput alter(
            @Parameter(description = "User ID", example = "1", required = true) Long userId,
            @RequestBody(description = "Updated user representation", required = true) UserUpdateInput userUpdateInput);

    @Operation(summary = "Delete an user")
    void remove(@Parameter(description = "User ID", example = "1", required = true) Long userId);

    @Operation(summary = "Update an user password")
    UserOutput alterPassword(
            @Parameter(description = "User ID", example = "1", required = true) Long userId,
            @RequestBody(description = "New user password representation", required = true) UserPasswordInput userPasswordInput);
}
