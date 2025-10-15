package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.GroupInput;
import com.github.edurbs.datsa.api.v1.dto.output.GroupOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name="security_auth")
@Tag(name = "Groups", description = "Groups registry")
public interface GroupControllerOpenApi {

    @Operation(summary = "List groups")
    CollectionModel<GroupOutput> getAll();

    @Operation(summary = "Get a group")
    GroupOutput getOne(@Parameter(description = "Group ID", example = "1", required = true) Long id);

    @Operation(summary = "Add a group")
    GroupOutput add(@RequestBody(description = "New group representation", required = true) GroupInput groupInput);

    @Operation(summary = "Update a group")
    GroupOutput alter(@Parameter(description = "Group ID", example = "1", required = true) Long id,
                      @RequestBody(description = "Updated Group representation", required = true) GroupInput input);

    @Operation(summary = "Delete a group")
    void remove(@Parameter(description = "Group ID", example = "1", required = true) Long id);
}
