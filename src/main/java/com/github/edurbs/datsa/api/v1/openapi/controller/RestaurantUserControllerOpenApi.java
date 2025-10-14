package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.output.UserOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@SecurityRequirement(name="security_auth")
public interface RestaurantUserControllerOpenApi {
    CollectionModel<UserOutput> getAllUsers(@PathVariable Long restaurantId);

    ResponseEntity<Void> disassociateUser(@PathVariable Long restaurantId, @PathVariable Long userId);

    ResponseEntity<Void> associateUser(@PathVariable Long restaurantId, @PathVariable Long userId);
}
