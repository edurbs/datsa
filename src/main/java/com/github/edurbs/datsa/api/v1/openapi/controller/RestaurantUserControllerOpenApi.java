package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.output.UserOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurants")
public interface RestaurantUserControllerOpenApi {

    @Operation(summary = "List users from a restaurant")
    CollectionModel<UserOutput> getAllUsers(
            @Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId);

    @Operation(summary = "Disassociate an user to a restaurant")
    ResponseEntity<Void> disassociateUser(
            @Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId,
            @Parameter(description = "User ID", example = "1", required = true) Long userId);

    @Operation(summary = "Associate an user to a restaurant")
    ResponseEntity<Void> associateUser(
            @Parameter(description = "Restaurant ID", example = "1", required = true) Long restaurantId,
            @Parameter(description = "User ID", example = "1", required = true) Long userId);
}
