package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.CityInput;
import com.github.edurbs.datsa.api.v1.dto.output.CityOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name="security_auth")
@Tag(name = "Cities", description = "Cities registry")
public interface CityControllerOpenApi {

    @Operation(summary = "List cities")
    CollectionModel<CityOutput> listAll();

    @Operation(summary = "Get a city",
        responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400",
                description = "Invalid City ID",
                content = @Content(schema = @Schema(ref = "Problem")))
        }
    )
    CityOutput getById(@Parameter(description = "City ID", example = "1", required = true) Long cityId);

    @Operation(summary = "Add a city", description = "To add a city, you must specify the city name and the state ID.")
    CityOutput add( @RequestBody(
            description = "New city representation",
            required = true,
            content = @Content(schema = @Schema(implementation = CityInput.class))
    ) CityInput cityInput);

    @Operation(summary = "Update a city")
    CityOutput alter(@Parameter(description = "City ID", example = "1", required = true) Long cityId,
                     @RequestBody(description = "Updated City representation", required = true) CityInput cityInput);

    @Operation(summary = "Delete a city")
    void delete(@Parameter(description = "City ID", example = "1", required = true) Long cityId);
}
