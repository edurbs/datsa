package com.github.edurbs.datsa.core.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

    public @interface Kitchens{

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_KITCHENS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanEdit {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULT_KITCHENS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanConsult {}

    }

    public @interface Restaurants{

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_RESTAURANTS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanEdit {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_RESTAURANTS') or @mySecurity.manageRestaurant(#restaurantId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanEditAndManage {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanConsult {}
    }

    public @interface Orders{

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULT_ORDERS') or @mySecurity.getUserId() == returnObject.user.id or @mySecurity.manageRestaurant(returnObject.restaurant.id)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanFindById {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULT_ORDERS') or @mySecurity.manageRestaurant(#orderFilter.restaurantId) or @mySecurity.getUserId() == #orderFilter.userId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanSearchWithFilter {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanEdit { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('MANAGE_ORDERS') or "
                + "@mySecurity.manageRestaurantOrder(#uuid))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanManageOrders {}
    }

    public @interface PaymentMethods{

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_PAYMENT_METHODS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanEdit { }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanConsult {}


    }

}
