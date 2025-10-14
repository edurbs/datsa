package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.output.PermissionOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name="security_auth")
public interface PermissionControllerOpenApi {
    CollectionModel<PermissionOutput> findAll();
}
