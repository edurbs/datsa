package com.github.edurbs.datsa.api.v1.controller;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.v1.dto.input.ProductInput;
import com.github.edurbs.datsa.api.v1.dto.output.ProductOutput;
import com.github.edurbs.datsa.api.v1.mapper.ProductMapper;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.domain.service.ProductRegistryService;
import com.github.edurbs.datsa.domain.service.RestaurantRegistryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/restaurants/{restaurantId}/products")
@AllArgsConstructor
public class RestaurantProductController {

    private RestaurantRegistryService restaurantRegistryService;
    private ProductRegistryService productRegistryService;
    private ProductMapper productMapper;

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping
    public CollectionModel<ProductOutput> getAll(@PathVariable Long restaurantId, @RequestParam(required = false) Boolean inactiveIncluded) {
        if(Boolean.TRUE.equals(inactiveIncluded)){
            return productMapper.toCollectionModel(restaurantRegistryService.getAllProducts(restaurantId));
        }
        return productMapper.toCollectionModel(restaurantRegistryService.getAllActiveProducts(restaurantId));
    }

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping("/{productId}")
    public ProductOutput getOne(@PathVariable Long restaurantId, @PathVariable Long productId) {
        return productMapper.toModel(restaurantRegistryService.getProduct(restaurantId, productId));
    }

    @CheckSecurity.Restaurants.CanEditAndManage
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductOutput add(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
        var restaurant = restaurantRegistryService.getById(restaurantId);
        var product = productMapper.toDomain(productInput);
        product.setRestaurant(restaurant);
        var productSaved = productRegistryService.save(product);
        return productMapper.toModel(productSaved);
    }

    @CheckSecurity.Restaurants.CanEditAndManage
    @PutMapping("/{productId}")
    public ProductOutput alter(@PathVariable Long restaurantId, @PathVariable Long productId, @RequestBody @Valid ProductInput productInput) {
        var product = productRegistryService.getByRestaurant(restaurantId, productId);
        productMapper.copyToDomain(productInput, product);
        var productSaved = productRegistryService.save(product);
        return productMapper.toModel(productSaved);
    }

    @CheckSecurity.Restaurants.CanEditAndManage
    @DeleteMapping("/{productId}")
    public void remove(@PathVariable Long restaurantId, @PathVariable Long productId){
        productRegistryService.remove(productId);
    }

}
