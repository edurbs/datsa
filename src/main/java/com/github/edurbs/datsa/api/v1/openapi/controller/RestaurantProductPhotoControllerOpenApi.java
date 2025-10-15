package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.ProductPhotoInput;
import com.github.edurbs.datsa.api.v1.dto.output.ProductPhotoOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@SecurityRequirement(name="security_auth")
@Tag(name = "Restaurants")
public interface RestaurantProductPhotoControllerOpenApi {
    ProductPhotoOutput updatePhoto(Long restaurantId, Long productId, ProductPhotoInput productPhotoInput) throws IOException;

    ResponseEntity<ResponseStatus> delete(Long restaurantId, Long productId);

    @Operation(summary = "Get a product photo from a restaurant", responses = {
        @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductPhotoOutput.class)),
            @Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
            @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))
        })
    })
    ProductPhotoOutput getPhoto(Long restaurantId, Long productId);

    @Operation(hidden = true)
    ResponseEntity<?> getPhotoData(Long restaurantId, Long productId, String acceptHeader) throws HttpMediaTypeNotAcceptableException;


}
