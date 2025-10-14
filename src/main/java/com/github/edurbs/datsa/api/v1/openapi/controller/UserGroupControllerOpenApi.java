package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.output.GroupOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@SecurityRequirement(name="security_auth")
public interface UserGroupControllerOpenApi {
    CollectionModel<GroupOutput> getAll(@PathVariable Long userId);

    ResponseEntity<Void> associate(@PathVariable Long userId, @PathVariable Long groupId);

    ResponseEntity<Void> dissociate(@PathVariable Long userId, @PathVariable Long groupId);
}
