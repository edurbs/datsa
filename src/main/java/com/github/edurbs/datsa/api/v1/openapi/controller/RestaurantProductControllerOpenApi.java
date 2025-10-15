package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.ProductInput;
import com.github.edurbs.datsa.api.v1.dto.output.ProductOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name="security_auth")
@Tag(name = "Products")
public interface RestaurantProductControllerOpenApi {

    @Operation(summary = "List products of a restaurant")
    CollectionModel<ProductOutput> getAll(
            @Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId,
            @Parameter(description = "Include the inactives restaurants in the list", example = "true", required = false) Boolean inactiveIncluded);

    @Operation(summary = "Get a product of a restaurant")
    ProductOutput getOne(
            @Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId,
            @Parameter(description = "Product ID", example = "1", required = true)  Long productId);

    @Operation(summary = "Add a product to a restaurant")
    ProductOutput add(
            @Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId,
            @RequestBody(description = "New product representation", required = true) ProductInput productInput);

    @Operation(summary = "Update a product of a restaurant")
    ProductOutput alter(
            @Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId,
            @Parameter(description = "Product ID", example = "1", required = true)  Long productId,
            @RequestBody(description = "Updated product representation", required = true) ProductInput productInput);

    @Operation(summary = "Remove a product of a restaurant")
    void remove(
            @Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId,
            @Parameter(description = "Product ID", example = "1", required = true)  Long productId);
}
