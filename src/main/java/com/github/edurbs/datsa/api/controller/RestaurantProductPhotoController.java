package com.github.edurbs.datsa.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.github.edurbs.datsa.api.dto.input.ProductPhotoInput;
import com.github.edurbs.datsa.api.dto.output.ProductPhotoOutput;
import com.github.edurbs.datsa.api.mapper.ProductPhotoMapper;
import com.github.edurbs.datsa.domain.model.Product;
import com.github.edurbs.datsa.domain.model.ProductPhoto;
import com.github.edurbs.datsa.domain.service.ProductPhotoCatalogService;
import com.github.edurbs.datsa.domain.service.ProductRegistryService;


@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {

    @Autowired
    ProductPhotoCatalogService productPhotoCatalogService;

    @Autowired
    ProductRegistryService productRegistryService;

    @Autowired
    ProductPhotoMapper productPhotoMapper;

    @Autowired
    PhotoStorageService photoStorageService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductPhotoOutput updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId, @Valid ProductPhotoInput productPhotoInput) throws IOException {
        Product product = productRegistryService.getByRestaurant(restaurantId, productId);
        MultipartFile file = productPhotoInput.getFile();
        ProductPhoto photo = new ProductPhoto();
        photo.setProduct(product);
        photo.setDescription(productPhotoInput.getDescription());
        photo.setContentType(file.getContentType());
        photo.setSize(file.getSize());
        photo.setFileName(file.getOriginalFilename());
        ProductPhoto savedPhoto = productPhotoCatalogService.save(photo, file.getInputStream());
        return productPhotoMapper.toOutput(savedPhoto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductPhotoOutput getPhoto(@PathVariable Long restaurantId, @PathVariable Long productId){
        Product product = productRegistryService.getByRestaurant(restaurantId, productId);
        ProductPhoto photo = productPhotoCatalogService.get(product);
        return productPhotoMapper.toOutput(photo);
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> getPhotoData(@PathVariable Long restaurantId, @PathVariable Long productId, @RequestHeader(name="accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            Product product = productRegistryService.getByRestaurant(restaurantId, productId);
            ProductPhoto photo = productPhotoCatalogService.get(product);
            MediaType photoMediaType = MediaType.parseMediaType(photo.getContentType());
            List<MediaType> acceptedMediaTypes = MediaType.parseMediaTypes(acceptHeader);
            checkCompatibility(photoMediaType, acceptedMediaTypes);
            InputStream photoData = productPhotoCatalogService.getData(photo);
            return ResponseEntity.ok()
                .contentType(photoMediaType)
                .body(new InputStreamResource(photoData));
        } catch (ModelNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void checkCompatibility(MediaType photoMediaType, List<MediaType> acceptedMediaTypes) throws HttpMediaTypeNotAcceptableException {
        boolean compatible = acceptedMediaTypes.stream()
            .anyMatch(mediaType -> mediaType.isCompatibleWith(photoMediaType));
        if(!compatible){
            throw new HttpMediaTypeNotAcceptableException(acceptedMediaTypes);
        }
    }

}
