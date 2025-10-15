package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.RestaurantInput;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantNameOutput;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantOutput;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantSummaryOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name="security_auth")
@Tag(name = "Restaurants", description = "Restaurants registry")
public interface RestaurantControllerOpenApi {

    @Operation(parameters = {
        @Parameter(
            name = "projection",
            description = "Projection name",
            example = "only-name",
            in = ParameterIn.QUERY,
            required = false
        )
    })
    CollectionModel<RestaurantSummaryOutput> listAll();

    @Operation(hidden = true)
    CollectionModel<RestaurantNameOutput> listAllOnlyName();

    @Operation(summary = "Get a restaurant")
    RestaurantOutput getById(@Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId);

    @Operation(summary = "Add a restaurant")
    ResponseEntity<RestaurantOutput> add(@RequestBody(description = "New restaurant representation", required = true) RestaurantInput restaurantInput);

    @Operation(summary = "Update a restaurant")
    RestaurantOutput alter(@Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId,
                           @RequestBody(description = "Updated restaurant representation", required = true) RestaurantInput restaurantInput);

    @Operation(summary = "Delete a restaurant")
    void delete(@Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId);

    @Operation(summary = "Activate a restaurant")
    ResponseEntity<Void> activate(@Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId);

    @Operation(summary = "Activate a list of restaurants")
    ResponseEntity<Void> activations(@Parameter(description = "List of restaurant ID's", required = true) List<Long> restaurantIds);

    @Operation(summary = "Inactivate a restaurant")
    ResponseEntity<Void> inactivate(@Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId);

    @Operation(summary = "Inactivate a list of restaurants")
    ResponseEntity<Void> inactivations(@Parameter(description = "List of restaurant ID's", required = true) List<Long> restaurantIds);

    @Operation(summary = "Open a restaurants")
    ResponseEntity<Void> open(@Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId);

    @Operation(summary = "Close a restaurants")
    ResponseEntity<Void> close(@Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId);
}
