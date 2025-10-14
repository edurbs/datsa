package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.ProductPhotoInput;
import com.github.edurbs.datsa.api.v1.dto.output.ProductPhotoOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.io.IOException;

@SecurityRequirement(name="security_auth")
@Tag(name = "Restaurants")
public interface RestaurantProductPhotoControllerOpenApi {
    ProductPhotoOutput updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId, @Valid ProductPhotoInput productPhotoInput) throws IOException;

    ProductPhotoOutput getPhoto(@PathVariable Long restaurantId, @PathVariable Long productId);

    ResponseEntity<ResponseStatus> delete(@PathVariable Long restaurantId, @PathVariable Long productId);

    ResponseEntity<?> getPhotoData(@PathVariable Long restaurantId, @PathVariable Long productId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException;


}
