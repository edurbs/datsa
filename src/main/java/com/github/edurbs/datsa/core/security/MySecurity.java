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
        Object userId = jwt.getClaim("user_id");
        if(userId == null){
            return null;
        }
        return Long.valueOf(userId.toString());
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

    public boolean isAuthenticated(){
        return getAuthentication().isAuthenticated();
    }

    public boolean hasWriteScope(){
        return hasAuthority("SCOPE_WRITE");
    }

    public boolean hasReadScope(){
        return hasAuthority("SCOPE_READ");
    }

    public boolean canConsultRestaurants(){
        return isAuthenticatedAndHasReadScope();
    }

    public boolean canEditRestaurants(){
        return hasWriteScope() && hasAuthority("EDIT_RESTAURANTS");
    }

    public boolean canEditAndManageRestaurant(Long restaurantId){
        return hasWriteScope() && (hasAuthority("EDIT_RESTAURANTS") || manageRestaurant(restaurantId));
    }

    public boolean canConsultUsersGroupsPermissions(){
        return hasReadScope() && hasAuthority("CONSULT_USERS_GROUPS_PERMISSIONS");
    }

    public boolean canEditUsersGroupsPermissions(){
        return hasWriteScope() && hasAuthority("EDIT_USERS_GROUPS_PERMISSIONS");
    }

    public boolean canSearchWithFilter(Long userId, Long restaurantId){
        return hasReadScope() && (hasAuthority("CONSULT_ORDERS") || authenticatedUserEquals(userId) || canEditAndManageRestaurant(restaurantId));
    }

    public boolean canSearchWithFilter(){
        return isAuthenticatedAndHasReadScope();
    }

    private boolean isAuthenticatedAndHasReadScope() {
        return isAuthenticated() && hasReadScope();
    }

    public boolean canConsultPaymentMethods(){
        return isAuthenticatedAndHasReadScope();
    }

    public boolean canConsultCities(){
        return isAuthenticatedAndHasReadScope();
    }

    public boolean canConsultStates(){
        return isAuthenticatedAndHasReadScope();
    }

    public boolean canConsultKitchens(){
        return isAuthenticatedAndHasReadScope();
    }

    public boolean canConsultStatistics(){
        return hasReadScope() && hasAuthority("GENERATE_REPORTS");
    }



}
