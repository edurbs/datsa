package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.output.GroupOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name="security_auth")
@Tag(name = "Users")
public interface UserGroupControllerOpenApi {

    @Operation(summary = "Get user groups")
    CollectionModel<GroupOutput> getAll(Long userId);

    @Operation(summary = "Associate an user to a group")
    ResponseEntity<Void> associate(
            @Parameter(description = "User ID", example = "1", required = true) Long userId,
            @Parameter(description = "Group ID", example = "1", required = true) Long groupId);

    @Operation(summary = "Disassociate an user from a group")
    ResponseEntity<Void> dissociate(
            @Parameter(description = "User ID", example = "1", required = true) Long userId,
            @Parameter(description = "Group ID", example = "1", required = true) Long groupId);
}
