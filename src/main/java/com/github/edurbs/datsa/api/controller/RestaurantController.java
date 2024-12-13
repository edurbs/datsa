package com.github.edurbs.datsa.api.controller;

import java.util.List;

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

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.Restaurant;
import com.github.edurbs.datsa.domain.service.RestaurantRegistryService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private static final String RESTAURANT_URL = "/restaurants";

    @Autowired
    private RestaurantRegistryService restaurantRegistryService;

    @GetMapping
    public List<Restaurant> listAll() {
        return restaurantRegistryService.getAll();
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getById(@PathVariable Long restaurantId) {
        try {
            return ResponseEntity.ok(restaurantRegistryService.getById(restaurantId));
        } catch (ModelNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant add(@RequestBody Restaurant restaurant) {
        return restaurantRegistryService.save(restaurant);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> alter(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
        try {
            var alteredRestaurant = restaurantRegistryService.getById(restaurantId);
            BeanUtils.copyProperties(restaurant, alteredRestaurant, "id");
            alteredRestaurant = restaurantRegistryService.save(alteredRestaurant);

            return ResponseEntity.ok(alteredRestaurant);
        } catch (ModelNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Void> delete(@PathVariable Long restaurantId) {
        try {
            restaurantRegistryService.remove(restaurantId);
            return ResponseEntity.noContent().build();
        } catch (ModelNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ModelInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
