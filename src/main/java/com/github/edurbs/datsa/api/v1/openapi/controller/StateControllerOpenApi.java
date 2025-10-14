package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.StateInput;
import com.github.edurbs.datsa.api.v1.dto.output.StateOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@SecurityRequirement(name="security_auth")
@Tag(name = "States", description = "States registry")
public interface StateControllerOpenApi {
    CollectionModel<StateOutput> listAll();

    StateOutput getById(@PathVariable Long stateId);

    StateOutput add(@RequestBody @Valid StateInput stateInput);

    StateOutput alter(@PathVariable Long stateId, @RequestBody @Valid StateInput stateInput);

    void delete(@PathVariable Long stateId);
}
