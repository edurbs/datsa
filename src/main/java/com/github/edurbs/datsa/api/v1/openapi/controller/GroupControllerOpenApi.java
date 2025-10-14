package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.GroupInput;
import com.github.edurbs.datsa.api.v1.dto.output.GroupOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@SecurityRequirement(name="security_auth")
@Tag(name = "Groups", description = "Groups registry")
public interface GroupControllerOpenApi {

    CollectionModel<GroupOutput> getAll();

    GroupOutput getOne(@PathVariable Long id);

    GroupOutput add(@RequestBody GroupInput groupInput);

    GroupOutput alter(@PathVariable Long id, @RequestBody GroupInput input);

    void remove(@PathVariable Long id);
}
