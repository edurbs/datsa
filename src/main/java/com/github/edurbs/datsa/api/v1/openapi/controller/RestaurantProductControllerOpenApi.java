package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.ProductInput;
import com.github.edurbs.datsa.api.v1.dto.output.ProductOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@SecurityRequirement(name="security_auth")
public interface RestaurantProductControllerOpenApi {
    CollectionModel<ProductOutput> getAll(@PathVariable Long restaurantId, @RequestParam(required = false) Boolean inactiveIncluded);

    ProductOutput getOne(@PathVariable Long restaurantId, @PathVariable Long productId);

    ProductOutput add(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput);

    ProductOutput alter(@PathVariable Long restaurantId, @PathVariable Long productId, @RequestBody @Valid ProductInput productInput);

    void remove(@PathVariable Long restaurantId, @PathVariable Long productId);
}
