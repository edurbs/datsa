package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.output.PermissionOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@SecurityRequirement(name="security_auth")
public interface GroupPermissionControllerOpenApi {
    CollectionModel<PermissionOutput> getAll(@PathVariable Long groupId);

    ResponseEntity<Void> associatePermission(@PathVariable Long groupId, @PathVariable Long permissionId);

    ResponseEntity<Void> dissociatePermission(@PathVariable Long groupId, @PathVariable Long permissionId);
}
