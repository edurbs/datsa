package com.github.edurbs.datsa.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.domain.repository.OrderRepository;
import com.github.edurbs.datsa.domain.repository.RestaurantRepository;

@Component
public class MySecurity {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId(){
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("user_id");
    }

    public boolean manageRestaurant(Long restaurantId){
        if(restaurantId==null){
            return false;
        }
        return restaurantRepository.existsByIdAndUsers_Id(restaurantId, getUserId());
    }

    public boolean manageRestaurantOrder(String uuid){
        return orderRepository.existsByUuidAndRestaurantUsersId(uuid, getUserId());
    }

    public boolean authenticatedUserEquals(Long userId){
        return getUserId() != null && userId != null && getUserId().equals(userId);
    }

    public boolean canManageOrders(String orderId){
        // @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('MANAGE_ORDERS') or "
        //        + "@mySecurity.manageRestaurantOrder(#uuid))")
        return hasAuthority("SCOPE_WRITE") && (hasAuthority("MANAGE_ORDERS") || manageRestaurantOrder(orderId));
    }

    public boolean hasAuthority(String authorityName){
        return getAuthentication().getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals(authorityName));
    }


}
