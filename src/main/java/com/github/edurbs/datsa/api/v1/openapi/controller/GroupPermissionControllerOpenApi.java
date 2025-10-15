package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.output.PermissionOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name="security_auth")
@Tag(name = "Groups")
public interface GroupPermissionControllerOpenApi {

    @Operation(summary = "List group permissions")
    CollectionModel<PermissionOutput> getAll(
            @Parameter(description = "Group ID", example = "1", required = true) Long groupId);

    @Operation(summary = "Associate a permission to a group")
    ResponseEntity<Void> associatePermission(
            @Parameter(description = "Group ID", example = "1", required = true) Long groupId,
            @Parameter(description = "Permission ID", example = "1", required = true) Long permissionId);

    @Operation(summary = "Dissociate a permission from a group")
    ResponseEntity<Void> dissociatePermission(
            @Parameter(description = "Group ID", example = "1", required = true) Long groupId,
            @Parameter(description = "Permission ID", example = "1", required = true) Long permissionId);
}
