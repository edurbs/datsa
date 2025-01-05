package com.github.edurbs.datsa.api.controller;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.dto.input.ProductInput;
import com.github.edurbs.datsa.api.dto.output.ProductOutput;
import com.github.edurbs.datsa.api.mapper.ProductMapper;
import com.github.edurbs.datsa.domain.service.ProductRegistryService;
import com.github.edurbs.datsa.domain.service.RestaurantRegistryService;

import lombok.AllArgsConstructor;





@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
@AllArgsConstructor
public class RestaurantProductController {

    private RestaurantRegistryService restaurantRegistryService;
    private ProductRegistryService productRegistryService;
    private ProductMapper productMapper;

    @GetMapping
    public Set<ProductOutput> getAll(@PathVariable Long restaurantId) {
        var list = productMapper.toOutputList(restaurantRegistryService.getAllProducts(restaurantId));
        return list.stream().collect(Collectors.toSet());
    }

    @GetMapping("/{productId}")
    public ProductOutput getOne(@PathVariable Long restaurantId, @PathVariable Long productId) {
        return productMapper.toOutput(restaurantRegistryService.getProduct(restaurantId, productId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductOutput add(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
        var restaurant = restaurantRegistryService.getById(restaurantId);
        var product = productMapper.toDomain(productInput);
        product.setRestaurant(restaurant);
        var productSaved = productRegistryService.save(product);
        return productMapper.toOutput(productSaved);
    }

    @PutMapping("/{productId}")
    public ProductOutput alter(@PathVariable Long restaurantId, @PathVariable Long productId, @RequestBody @Valid ProductInput productInput) {
        var product = productRegistryService.getByRestaurant(restaurantId, productId);
        productMapper.copyToDomain(productInput, product);
        var productSaved = productRegistryService.save(product);
        return productMapper.toOutput(productSaved);
    }

    @DeleteMapping("/{productId}")
    public void remove(@PathVariable Long restaurantId, @PathVariable Long productId){
        productRegistryService.remove(productId);
    }

}
