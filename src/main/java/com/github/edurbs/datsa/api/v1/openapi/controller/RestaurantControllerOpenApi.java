package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.RestaurantInput;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantNameOutput;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantOutput;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantSummaryOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

    RestaurantOutput getById(Long restaurantId);

    ResponseEntity<RestaurantOutput> add(RestaurantInput restaurantInput);

    RestaurantOutput alter(Long restaurantId,
                           RestaurantInput restaurantInput);

    void delete(Long restaurantId);

    ResponseEntity<Void> activate(Long restaurantId);

    ResponseEntity<Void> activations(List<Long> restaurantIds);

    ResponseEntity<Void> inactivate(Long restaurantId);

    ResponseEntity<Void> inactivations(List<Long> restaurantIds);

    ResponseEntity<Void> open(Long restaurantId);

    ResponseEntity<Void> close(Long restaurantId);
}
