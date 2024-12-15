package com.github.edurbs.datsa.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("When create a valid Restaurant, should not be violations")
    void one () {
        var restaurant = restaurantFactory();

        assertTrue(violations(restaurant).isEmpty());
    }

    @Test
    @DisplayName("When create a Restaurant with null Kitchen, then should be one violation")
    void two () {
        var restaurant = restaurantFactory();
        restaurant.setKitchen(null);

        assertEquals(1, violations(restaurant).size());
    }

    @Test
    @DisplayName("When create a Restaurant with Kitchen with null id, then should be one violation")
    void three () {
        var restaurant = restaurantFactory();
        restaurant.getKitchen().setId(null);

        assertEquals(1, violations(restaurant).size());
    }
}
