package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.KitchenInput;
import com.github.edurbs.datsa.api.v1.dto.output.KitchenOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@SecurityRequirement(name="security_auth")
@Tag(name="Kitchens", description = "Kitchens registry")
public interface KitchenControllerOpenApi {
    PagedModel<KitchenOutput> listAll(Pageable pageable);

    KitchenOutput getById(@PathVariable Long kitchenId);

    KitchenOutput add(@RequestBody @Valid KitchenInput kitchenInput);

    KitchenOutput alter(@PathVariable Long kitchenId, @RequestBody @Valid KitchenInput kitchenInput);

    void delete(@PathVariable Long kitchenId);
}
