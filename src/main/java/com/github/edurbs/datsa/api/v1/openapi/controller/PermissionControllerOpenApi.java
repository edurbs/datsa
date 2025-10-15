package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.output.PermissionOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name="security_auth")
@Tag(name = "Permissions", description = "Permissions registry")
public interface PermissionControllerOpenApi {

    @Operation(summary = "List permissions")
    CollectionModel<PermissionOutput> findAll();
}
