package com.github.edurbs.datsa.api.controller;

import java.util.Set;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.LinksAdder;
import com.github.edurbs.datsa.api.dto.output.UserOutput;
import com.github.edurbs.datsa.api.mapper.UserMapper;
import com.github.edurbs.datsa.domain.model.Restaurant;
import com.github.edurbs.datsa.domain.model.User;
import com.github.edurbs.datsa.domain.service.RestaurantRegistryService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/restaurants/{restaurantId}/users")
@RequiredArgsConstructor
public class RestaurantUserController {

    private final RestaurantRegistryService restaurantRegistryService;
    private final UserMapper userMapper;
    private final LinksAdder linksAdder;

    @GetMapping
    public CollectionModel<UserOutput> getAllUsers(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantRegistryService.getById(restaurantId);
        Set<User> users = restaurant.getUsers();
        return userMapper.toCollectionModel(users)
            .removeLinks()
            .add(linksAdder.toRestaurantUsers(restaurantId));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociateUser(@PathVariable Long restaurantId, @PathVariable Long userId){
        restaurantRegistryService.disassociateUser(restaurantId, userId);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associateUser(@PathVariable Long restaurantId, @PathVariable Long userId) {
        restaurantRegistryService.associateUser(restaurantId, userId);
    }


}
