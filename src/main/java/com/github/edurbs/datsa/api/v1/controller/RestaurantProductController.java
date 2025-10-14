package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.dto.input.ProductInput;
import com.github.edurbs.datsa.api.v1.dto.output.ProductOutput;
import com.github.edurbs.datsa.api.v1.mapper.ProductMapper;
import com.github.edurbs.datsa.api.v1.openapi.controller.RestaurantProductControllerOpenApi;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.domain.service.ProductRegistryService;
import com.github.edurbs.datsa.domain.service.RestaurantRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/restaurants/{restaurantId}/products")
@AllArgsConstructor
public class RestaurantProductController implements RestaurantProductControllerOpenApi {

    private RestaurantRegistryService restaurantRegistryService;
    private ProductRegistryService productRegistryService;
    private ProductMapper productMapper;

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping
    @Override
    public CollectionModel<ProductOutput> getAll(@PathVariable Long restaurantId, @RequestParam(required = false) Boolean inactiveIncluded) {
        if(Boolean.TRUE.equals(inactiveIncluded)){
            return productMapper.toCollectionModel(restaurantRegistryService.getAllProducts(restaurantId));
        }
        return productMapper.toCollectionModel(restaurantRegistryService.getAllActiveProducts(restaurantId));
    }

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping("/{productId}")
    @Override
    public ProductOutput getOne(@PathVariable Long restaurantId, @PathVariable Long productId) {
        return productMapper.toModel(restaurantRegistryService.getProduct(restaurantId, productId));
    }

    @CheckSecurity.Restaurants.CanEditAndManage
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ProductOutput add(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
        var restaurant = restaurantRegistryService.getById(restaurantId);
        var product = productMapper.toDomain(productInput);
        product.setRestaurant(restaurant);
        var productSaved = productRegistryService.save(product);
        return productMapper.toModel(productSaved);
    }

    @CheckSecurity.Restaurants.CanEditAndManage
    @PutMapping("/{productId}")
    @Override
    public ProductOutput alter(@PathVariable Long restaurantId, @PathVariable Long productId, @RequestBody @Valid ProductInput productInput) {
        var product = productRegistryService.getByRestaurant(restaurantId, productId);
        productMapper.copyToDomain(productInput, product);
        var productSaved = productRegistryService.save(product);
        return productMapper.toModel(productSaved);
    }

    @CheckSecurity.Restaurants.CanEditAndManage
    @DeleteMapping("/{productId}")
    @Override
    public void remove(@PathVariable Long restaurantId, @PathVariable Long productId){
        productRegistryService.remove(productId);
    }

}
