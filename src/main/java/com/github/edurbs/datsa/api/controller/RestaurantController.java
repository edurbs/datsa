package com.github.edurbs.datsa.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.domain.model.Restaurant;
import com.github.edurbs.datsa.domain.service.RestaurantRegistryService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRegistryService restaurantRegistryService;

    @GetMapping
    public List<Restaurant> listAll() {
        return restaurantRegistryService.getAll();
    }

    @GetMapping("/{restaurantId}")
    public Restaurant getById(@PathVariable Long restaurantId) {
        return restaurantRegistryService.getById(restaurantId);
    }

    @PostMapping
    public ResponseEntity<Restaurant> add(@RequestBody @Valid Restaurant restaurant) {
        var restaurantAdded = restaurantRegistryService.save(restaurant);
        URI uri = URI.create("/restaurants/"+restaurantAdded.getId());
        return ResponseEntity.created(uri).body(restaurantAdded);
    }

    @PutMapping("/{restaurantId}")
    public Restaurant alter(@PathVariable Long restaurantId, @RequestBody @Valid Restaurant restaurant) {
        var alteredRestaurant = restaurantRegistryService.getById(restaurantId);
        BeanUtils.copyProperties(restaurant, alteredRestaurant, "id", "paymentMethods", "address", "registrationDate",
                "products");
        return restaurantRegistryService.save(alteredRestaurant);
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long restaurantId) {
            restaurantRegistryService.remove(restaurantId);
    }
}
