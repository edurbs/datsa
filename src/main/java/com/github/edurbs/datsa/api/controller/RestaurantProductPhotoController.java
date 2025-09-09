package com.github.edurbs.datsa.api.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.dto.input.ProductPhotoInput;


@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId, @Valid ProductPhotoInput productPhotoInput) {
        var fileName = UUID.randomUUID().toString()+"_"+productPhotoInput.getFile().getOriginalFilename();
        var filePhoto = Path.of("/tmp/photos", fileName);
        try {
			productPhotoInput.getFile().transferTo(filePhoto);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
    }

}
