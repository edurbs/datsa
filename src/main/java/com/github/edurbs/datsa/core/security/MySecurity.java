package com.github.edurbs.datsa.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.domain.repository.RestaurantRepository;

@Component
public class MySecurity {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId(){
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("user_id");
    }

    public boolean manageRestaurant(Long restaurantId){
        return restaurantRepository.existsByIdAndUsers_Id(restaurantId, getUserId());
    }

}
