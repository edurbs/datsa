package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.StateInput;
import com.github.edurbs.datsa.api.v1.dto.output.StateOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name="security_auth")
@Tag(name = "States", description = "States registry")
public interface StateControllerOpenApi {

    @Operation(summary = "List states")
    CollectionModel<StateOutput> listAll();

    @Operation(summary = "Get a state")
    StateOutput getById(@Parameter(description = "State ID", example = "1", required = true) Long stateId);

    @Operation(summary = "Add a state")
    StateOutput add(@RequestBody(description = "New state representation", required = true) StateInput stateInput);

    @Operation(summary = "Update a state")
    StateOutput alter(@Parameter(description = "State ID", example = "1", required = true) Long stateId,
                      @RequestBody(description = "New state representation", required = true) StateInput stateInput);

    @Operation(summary = "Delete a state")
    void delete(@Parameter(description = "State ID", example = "1", required = true) Long stateId);
}
