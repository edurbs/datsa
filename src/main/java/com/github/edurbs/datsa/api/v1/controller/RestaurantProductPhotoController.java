package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.dto.input.ProductPhotoInput;
import com.github.edurbs.datsa.api.v1.dto.output.ProductPhotoOutput;
import com.github.edurbs.datsa.api.v1.mapper.ProductPhotoMapper;
import com.github.edurbs.datsa.api.v1.openapi.controller.RestaurantProductPhotoControllerOpenApi;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.Product;
import com.github.edurbs.datsa.domain.model.ProductPhoto;
import com.github.edurbs.datsa.domain.service.PhotoStorageService;
import com.github.edurbs.datsa.domain.service.PhotoStorageService.FetchedPhoto;
import com.github.edurbs.datsa.domain.service.ProductPhotoCatalogService;
import com.github.edurbs.datsa.domain.service.ProductRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/v1/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController implements RestaurantProductPhotoControllerOpenApi {

    @Autowired
    ProductPhotoCatalogService productPhotoCatalogService;

    @Autowired
    ProductRegistryService productRegistryService;

    @Autowired
    ProductPhotoMapper productPhotoMapper;

    @Autowired
    PhotoStorageService photoStorageService;

    @CheckSecurity.Restaurants.CanEditAndManage
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
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
        return productPhotoMapper.toModel(savedPhoto);
    }

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ProductPhotoOutput getPhoto(@PathVariable Long restaurantId, @PathVariable Long productId){
        Product product = productRegistryService.getByRestaurant(restaurantId, productId);
        ProductPhoto photo = productPhotoCatalogService.get(product);
        return productPhotoMapper.toModel(photo);
    }

    @CheckSecurity.Restaurants.CanEditAndManage
    @DeleteMapping
    @Override
    public ResponseEntity<ResponseStatus> delete(@PathVariable Long restaurantId, @PathVariable Long productId){
        Product product = productRegistryService.getByRestaurant(restaurantId, productId);
        ProductPhoto photo = productPhotoCatalogService.get(product);
        productPhotoCatalogService.delete(photo);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping
    @Override
    public ResponseEntity<?> getPhotoData(@PathVariable Long restaurantId, @PathVariable Long productId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            Product product = productRegistryService.getByRestaurant(restaurantId, productId);
            ProductPhoto photo = productPhotoCatalogService.get(product);
            MediaType photoMediaType = MediaType.parseMediaType(photo.getContentType());
            List<MediaType> acceptedMediaTypes = MediaType.parseMediaTypes(acceptHeader);
            checkCompatibility(photoMediaType, acceptedMediaTypes);
            FetchedPhoto fetchedPhoto = productPhotoCatalogService.getPhoto(photo);
            if(fetchedPhoto.hasUrl()){
                return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, fetchedPhoto.getUrl())
                    .build();
            }else{
                return ResponseEntity.ok()
                    .contentType(photoMediaType)
                    .body(new InputStreamResource(fetchedPhoto.getInputStream()));
            }
        } catch (ModelNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void checkCompatibility(MediaType photoMediaType, List<MediaType> acceptedMediaTypes) throws HttpMediaTypeNotAcceptableException {
        boolean compatible = acceptedMediaTypes.stream()
            .anyMatch(mediaType -> mediaType.isCompatibleWith(photoMediaType));
        if (!compatible) {
            throw new HttpMediaTypeNotAcceptableException(acceptedMediaTypes);
        }
    }

}
