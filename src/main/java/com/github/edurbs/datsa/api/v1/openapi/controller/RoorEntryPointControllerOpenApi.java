package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.controller.RoorEntryPointController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.RepresentationModel;

@SecurityRequirement(name="security_auth")
public interface RoorEntryPointControllerOpenApi {
    RootEntryPointModel root();

    public static class RootEntryPointModel extends RepresentationModel<RoorEntryPointController.RootEntryPointModel> {

    }
}
