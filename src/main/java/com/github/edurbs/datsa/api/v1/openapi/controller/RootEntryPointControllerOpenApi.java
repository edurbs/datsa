package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.controller.RootEntryPointController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.RepresentationModel;

@SecurityRequirement(name="security_auth")
@Tag(name = "Root", description = "Root entry point" )
public interface RootEntryPointControllerOpenApi {
    RootEntryPointModel root();

    public static class RootEntryPointModel extends RepresentationModel<RootEntryPointController.RootEntryPointModel> {

    }
}
