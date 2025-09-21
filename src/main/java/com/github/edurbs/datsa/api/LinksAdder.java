package com.github.edurbs.datsa.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.edurbs.datsa.api.controller.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

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

    public Link toRestaurantOpen(Long restaurantId){
        return linkTo(methodOn(RestaurantController.class).open(restaurantId)).withRel("open");
    }
    public Link toRestaurantClose(Long restaurantId){
        return linkTo(methodOn(RestaurantController.class).close(restaurantId)).withRel("close");
    }
    public Link toRestaurantActivate(Long restaurantId){
        return linkTo(methodOn(RestaurantController.class).activate(restaurantId)).withRel("activate");
    }
    public Link toRestaurantInactivate(Long restaurantId){
        return linkTo(methodOn(RestaurantController.class).inactivate(restaurantId)).withRel("inactivate");
    }

    public Link toRestaurant(Long restaurantId) {
        return linkTo(methodOn(RestaurantController.class).getById(restaurantId)).withSelfRel();
    }

    public Link toRestaurants() {
                String restaurantUrl = linkTo(RestaurantController.class).toUri().toString();
        TemplateVariables variables = new TemplateVariables( new TemplateVariable("projection", VariableType.REQUEST_PARAM));
        return Link.of(UriTemplate.of(restaurantUrl, variables), "restaurants");
    }

    public Link toRestaurantProduct(Long restaurantId, Long productId, String rel) {
        return linkTo(methodOn(RestaurantProductController.class).getOne(restaurantId, productId)).withRel(rel);
    }

    public Link toProducts(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantProductController.class).getOne(restaurantId, null)).withRel(rel);
    }

    public Link toProducts(Long restaurantId) {
        return linkTo(methodOn(RestaurantProductController.class).getAll(restaurantId, null)).withSelfRel();
    }

    public Link toProductPhoto(Long restaurantId, Long productId) {
        return linkTo(methodOn(RestaurantProductPhotoController.class).getPhoto(restaurantId, productId)).withSelfRel();
    }

    public Link toProductPhoto(Long restaurantId, Long productId, String rel) {
        return linkTo(methodOn(RestaurantProductPhotoController.class).getPhoto(restaurantId, productId)).withRel(rel);
    }

    public Link toRestaurantPaymentMethods(Long restaurantId){
        return linkTo(methodOn(RestaurantPaymentMethodController.class).listAll(restaurantId)).withRel("payment-methods");
    }

    public Link toDisassociatePaymentMethod(Long restaurantId, Long paymentMethodId, String rel) {
        return linkTo(methodOn(RestaurantPaymentMethodController.class).disassociate(restaurantId, paymentMethodId)).withRel(rel);
    }

    public Link associatePaymentMethod(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantPaymentMethodController.class).associate(restaurantId, null)).withRel(rel);
    }

    public Link toRestaurantUsers(Long restaurantId, String rel){
        return linkTo(methodOn(RestaurantUserController.class).getAllUsers(restaurantId)).withRel(rel);
    }

    public Link toRestaurantUsers(Long restaurantId){
        return linkTo(methodOn(RestaurantUserController.class).getAllUsers(restaurantId)).withSelfRel();
    }


    public Link toAssociateUser(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantUserController.class).associateUser(restaurantId, null)).withRel(rel);
    }

    public Link toDisassociateUser(Long restaurantId, Long userId, String rel) {
        return linkTo(methodOn(RestaurantUserController.class).disassociateUser(restaurantId, userId)).withRel(rel);
    }

    public Link toUser(Long userId) {
        return linkTo(methodOn(UserController.class).getOne(userId)).withSelfRel();
    }

    public Link toUsers() {
        return linkTo(methodOn(UserController.class).getAll()).withSelfRel();
    }

    public Link toUsers(String rel) {
        return linkTo(methodOn(UserController.class).getAll()).withRel(rel);
    }

    public Link toUserGroups(Long userId) {
        return linkTo(methodOn(UserGroupController.class).getAll(userId)).withRel("groups");
    }

    public Link toGroups() {
        return linkTo(methodOn(GroupController.class).getAll()).withSelfRel();
    }

    public Link toGroups(String rel) {
        return linkTo(methodOn(GroupController.class).getAll()).withRel(rel);
    }

    public Link toPaymentMethod(Long paymentMethodId) {
        return linkTo(methodOn(PaymentMethodController.class).getById(paymentMethodId, null)).withSelfRel();
    }

    public Link toPaymentMethods(){
        return linkTo(PaymentMethodController.class).withSelfRel();
    }

    public Link toPaymentMethods(String rel){
        return linkTo(PaymentMethodController.class).withRel(rel);
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

    public Link toPermissions(Long groupId, String rel) {
        return linkTo(methodOn(GroupPermissionController.class).getAll(groupId)).withRel(rel);
    }
}
