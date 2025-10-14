package com.github.edurbs.datsa.api.v1.openapi.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@SecurityRequirement(name="security_auth")
public interface StatusOrderControllerOpenApi {
    ResponseEntity<Void> confirm(@PathVariable String uuid);

    ResponseEntity<Void> cancel(@PathVariable String uuid);

    ResponseEntity<Void> delivery(@PathVariable String uuid);
}
