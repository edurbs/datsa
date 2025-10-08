package com.github.edurbs.datsa.api.v1.controller;

import java.util.Set;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.dto.output.UserOutput;
import com.github.edurbs.datsa.api.v1.mapper.UserMapper;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.Restaurant;
import com.github.edurbs.datsa.domain.model.MyUser;
import com.github.edurbs.datsa.domain.service.RestaurantRegistryService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/v1/restaurants/{restaurantId}/users")
@RequiredArgsConstructor
public class RestaurantUserController {

    private final RestaurantRegistryService restaurantRegistryService;
    private final UserMapper userMapper;
    private final LinksAdder linksAdder;
    private final MySecurity mySecurity;

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping
    public CollectionModel<UserOutput> getAllUsers(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantRegistryService.getById(restaurantId);
        Set<MyUser> users = restaurant.getUsers();
        CollectionModel<UserOutput> collectionModel = userMapper.toCollectionModel(users)
            .removeLinks();
        collectionModel.add(linksAdder.toRestaurantUsers(restaurantId));
        if(this.mySecurity.canEditAndManageRestaurant(restaurantId)){
            collectionModel.add(linksAdder.toAssociateUser(restaurantId, "associate"));
            collectionModel.getContent().forEach(userOutput -> {
                userOutput.add(linksAdder.toDisassociateUser(restaurantId, userOutput.getId(), "disassociate"));
            });

        }
        return collectionModel;
    }

    @CheckSecurity.Restaurants.CanEdit
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> disassociateUser(@PathVariable Long restaurantId, @PathVariable Long userId){
        restaurantRegistryService.disassociateUser(restaurantId, userId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.CanEdit
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associateUser(@PathVariable Long restaurantId, @PathVariable Long userId) {
        restaurantRegistryService.associateUser(restaurantId, userId);
        return ResponseEntity.noContent().build();
    }


}
