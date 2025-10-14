package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.CityInput;
import com.github.edurbs.datsa.api.v1.dto.output.CityOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@SecurityRequirement(name="security_auth")
@Tag(name = "Cities", description = "Cities registry")
public interface CityControllerOpenApi {
    CollectionModel<CityOutput> listAll();

    CityOutput getById(@PathVariable Long cityId);

    CityOutput add(@RequestBody @Valid CityInput cityInput);

    CityOutput alter(@PathVariable Long cityId, @RequestBody @Valid CityInput cityInput);

    void delete(@PathVariable Long cityId);
}
