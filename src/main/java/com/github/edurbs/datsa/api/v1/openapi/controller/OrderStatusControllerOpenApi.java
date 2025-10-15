package com.github.edurbs.datsa.api.v1.openapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name="security_auth")
@Tag(name = "Orders")
public interface OrderStatusControllerOpenApi {

    @Operation(summary = "Set an order as confirmed")
    ResponseEntity<Void> confirm(@Parameter(description = "Order UUID", example = "8788ce97-cdf1-4271-bf44-a461cf1c2656", required = true) String uuid);

    @Operation(summary = "Set an order as cancelled")
    ResponseEntity<Void> cancel(@Parameter(description = "Order UUID", example = "8788ce97-cdf1-4271-bf44-a461cf1c2656", required = true) String uuid);

    @Operation(summary = "Set an order as delivered")
    ResponseEntity<Void> delivery(@Parameter(description = "Order UUID", example = "8788ce97-cdf1-4271-bf44-a461cf1c2656", required = true) String uuid);
}
