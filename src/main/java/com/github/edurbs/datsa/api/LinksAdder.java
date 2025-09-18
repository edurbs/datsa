package com.github.edurbs.datsa.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.controller.CityController;
import com.github.edurbs.datsa.api.controller.GroupController;
import com.github.edurbs.datsa.api.controller.KitchenController;
import com.github.edurbs.datsa.api.controller.OrderController;
import com.github.edurbs.datsa.api.controller.PaymentMethodController;
import com.github.edurbs.datsa.api.controller.RestaurantController;
import com.github.edurbs.datsa.api.controller.RestaurantPaymentMethodController;
import com.github.edurbs.datsa.api.controller.RestaurantProductController;
import com.github.edurbs.datsa.api.controller.RestaurantUserController;
import com.github.edurbs.datsa.api.controller.StateController;
import com.github.edurbs.datsa.api.controller.StatusOrderController;
import com.github.edurbs.datsa.api.controller.UserController;

@Component
public class LinksAdder {

    private final TemplateVariables pageVariables = new TemplateVariables(
            new TemplateVariable("page", VariableType.REQUEST_PARAM),
            new TemplateVariable("size", VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", VariableType.REQUEST_PARAM));
    private final TemplateVariables filterVariables = new TemplateVariables(
            new TemplateVariable("userId", VariableType.REQUEST_PARAM),
            new TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
            new TemplateVariable("beginCreationDate", VariableType.REQUEST_PARAM),
            new TemplateVariable("endCreationDate", VariableType.REQUEST_PARAM));

    public Link toOrders() {
        String orderUrl = linkTo(OrderController.class).toUri().toString();
        return Link.of(UriTemplate.of(orderUrl, pageVariables.concat(filterVariables)), "orders");
    }

    public Link toOrderConfirm(String orderUUID, String rel) {
        return linkTo(methodOn(StatusOrderController.class).confirm(orderUUID)).withRel(rel);
    }

    public Link toOrderCancel(String orderUUID, String rel) {
        return linkTo(methodOn(StatusOrderController.class).cancel(orderUUID)).withRel(rel);
    }

    public Link toOrderDelivery(String orderUUID, String rel) {
        return linkTo(methodOn(StatusOrderController.class).delivery(orderUUID)).withRel(rel);
    }

    public Link toRestaurant(Long restaurantId) {
        return linkTo(methodOn(RestaurantController.class).getById(restaurantId)).withSelfRel();
    }

    public Link toRestaurants() {
        return linkTo(RestaurantController.class).withSelfRel();
    }

    public Link toRestaurantProduct(Long restaurantId, Long productId, String rel) {
        return linkTo(methodOn(RestaurantProductController.class).getOne(restaurantId, productId)).withRel(rel);
    }

    public Link toRestaurantPaymentMethods(Long restaurantId){
        return linkTo(methodOn(RestaurantPaymentMethodController.class).listAll(restaurantId)).withSelfRel();
    }

    public Link toRestaurantUsers(Long restaurantId){
        return linkTo(methodOn(RestaurantUserController.class).getAllUsers(restaurantId)).withSelfRel();
    }

    public Link toUser(Long userId) {
        return linkTo(methodOn(UserController.class).getOne(userId)).withSelfRel();
    }

    public Link toUsers() {
        return linkTo(methodOn(UserController.class).getAll()).withRel("users");
    }

    public Link toGroups() {
        return linkTo(methodOn(GroupController.class).getAll()).withRel("groups");
    }

    public Link toPaymentMethod(Long paymentMethodId) {
        return linkTo(methodOn(PaymentMethodController.class).getById(paymentMethodId, null)).withSelfRel();
    }

    public Link toCity(Long cityId) {
        return linkTo(methodOn(CityController.class).getById(cityId)).withSelfRel();
    }

    public Link toCities() {
        return linkTo(methodOn(CityController.class).listAll()).withRel("cities");
    }

    public Link toState(Long stateId) {
        return linkTo(methodOn(StateController.class).getById(stateId)).withSelfRel();
    }

    public Link toStates() {
        return linkTo(methodOn(StateController.class).listAll()).withRel("states");
    }

    public Link toKitchen(Long kitchenId) {
        return linkTo(methodOn(KitchenController.class).getById(kitchenId)).withSelfRel();
    }

    public Link toKitchens() {
        return linkTo(KitchenController.class
            ).withRel("kitchens");
    }



}
