package com.github.edurbs.datsa.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.dto.input.RestaurantInput;
import com.github.edurbs.datsa.api.dto.output.RestaurantOutput;
import com.github.edurbs.datsa.domain.model.Kitchen;
import com.github.edurbs.datsa.domain.model.Restaurant;

@Component
public class RestaurantMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurant toDomain(RestaurantInput restaurantInput) {
        return modelMapper.map(restaurantInput, Restaurant.class);
    }

    public void copyToDomain(RestaurantInput restaurantInput, Restaurant restaurant) {
        restaurant.setKitchen(new Kitchen());
        modelMapper.map(restaurantInput, restaurant);
    }

    public RestaurantOutput toOutput(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantOutput.class);
    }

    public List<RestaurantOutput> toOutputList(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(this::toOutput)
                .toList();
    }

}
