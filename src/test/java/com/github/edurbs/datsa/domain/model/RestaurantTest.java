package com.github.edurbs.datsa.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestaurantTest {

    @Autowired
    private Validator validator;

    private Restaurant restaurantFactory(){
        return Instancio.create(Restaurant.class);
    }

    private Set<ConstraintViolation<Restaurant>> violations(Restaurant restaurant){
        return validator.validate(restaurant);
    }

    @Test
    void whenCreateValidRestaurant_thenCreate() {
        var restaurant = restaurantFactory();

        assertTrue(violations(restaurant).isEmpty());
    }

    @Test
    void whenCreateRestaurantWithNullKitchen_thenInvalidate() {
        var restaurant = restaurantFactory();
        restaurant.setKitchen(null);

        assertEquals(1, violations(restaurant).size());
    }

    @Test
    void whenCreateRestauranteWithKithenIdNull_thenInvalidate() {
        var restaurant = restaurantFactory();
        restaurant.getKitchen().setId(null);

        assertEquals(1, violations(restaurant).size());
    }
}
