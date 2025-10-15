package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.KitchenInput;
import com.github.edurbs.datsa.api.v1.dto.output.KitchenOutput;
import com.github.edurbs.datsa.core.springdoc.PageableParameter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@SecurityRequirement(name="security_auth")
@Tag(name="Kitchens", description = "Kitchens registry")
public interface KitchenControllerOpenApi {

    @PageableParameter
    PagedModel<KitchenOutput> listAll(@Parameter(hidden = true) Pageable pageable);

    KitchenOutput getById(Long kitchenId);

    KitchenOutput add(KitchenInput kitchenInput);

    KitchenOutput alter(Long kitchenId, KitchenInput kitchenInput);

    void delete(Long kitchenId);
}
