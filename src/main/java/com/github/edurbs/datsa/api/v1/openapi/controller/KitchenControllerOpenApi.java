package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.KitchenInput;
import com.github.edurbs.datsa.api.v1.dto.output.KitchenOutput;
import com.github.edurbs.datsa.core.springdoc.PageableParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@SecurityRequirement(name="security_auth")
@Tag(name="Kitchens", description = "Kitchens registry")
public interface KitchenControllerOpenApi {

    @PageableParameter
    @Operation(summary = "List kitchens")
    PagedModel<KitchenOutput> listAll(@Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Get a kitchen")
    KitchenOutput getById(@Parameter(description = "Kitchen ID", example = "1", required = true) Long kitchenId);

    @Operation(summary = "Add a kitchen")
    KitchenOutput add(@RequestBody(description = "New Kitchen representation", required = true) KitchenInput kitchenInput);

    @Operation(summary = "Update a kitchen")
    KitchenOutput alter(@Parameter(description = "Kitchen ID", example = "1", required = true) Long kitchenId, @RequestBody(description = "Updated Kitchen representation", required = true) KitchenInput kitchenInput);

    @Operation(summary = "Delete a kitchen")
    void delete(@Parameter(description = "Kitchen ID", example = "1", required = true) Long kitchenId);
}
