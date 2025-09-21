package com.github.edurbs.datsa.api.v1;

import com.github.edurbs.datsa.api.v1.controller.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LinksAdder {

    private final TemplateVariables pageVariables = new TemplateVariables(
        new TemplateVariable("page", VariableType.REQUEST_PARAM),
        new TemplateVariable("size", VariableType.REQUEST_PARAM),
        new TemplateVariable("sort", VariableType.REQUEST_PARAM));
    private final TemplateVariables orderFilterVariables = new TemplateVariables(
        new TemplateVariable("userId", VariableType.REQUEST_PARAM),
        new TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
        new TemplateVariable("beginCreationDate", VariableType.REQUEST_PARAM),
        new TemplateVariable("endCreationDate", VariableType.REQUEST_PARAM));
    private final TemplateVariables dailySalesFilterVariables = new TemplateVariables(
        new TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
        new TemplateVariable("beginCreationDate", VariableType.REQUEST_PARAM),
        new TemplateVariable("endCreationDate", VariableType.REQUEST_PARAM),
        new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM)
    );

    public Link toOrders() {
        String orderUrl = linkTo(OrderController.class).toUri().toString();
        return Link.of(UriTemplate.of(orderUrl, pageVariables.concat(orderFilterVariables)), "orders");
    }

    public Link toOrders(String rel) {
        return linkTo(OrderController.class).withRel(rel);
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

    public Link toStatistics(String rel) {
        return linkTo(StatisticsController.class).withRel(rel);
    }

    public Link toDailySales() {
        String statisticsUrl = linkTo(methodOn(StatisticsController.class).getDailySales(null,null)).toUri().toString();
        return Link.of(UriTemplate.of(statisticsUrl, pageVariables.concat(dailySalesFilterVariables)),"daily-sales");
    }

    public Link toRestaurantOpen(Long restaurantId) {
        return linkTo(methodOn(RestaurantController.class).open(restaurantId)).withRel("open");
    }

    public Link toRestaurantClose(Long restaurantId) {
        return linkTo(methodOn(RestaurantController.class).close(restaurantId)).withRel("close");
    }

    public Link toRestaurantActivate(Long restaurantId) {
        return linkTo(methodOn(RestaurantController.class).activate(restaurantId)).withRel("activate");
    }

    public Link toRestaurantInactivate(Long restaurantId) {
        return linkTo(methodOn(RestaurantController.class).inactivate(restaurantId)).withRel("inactivate");
    }

    public Link toRestaurant(Long restaurantId) {
        return linkTo(methodOn(RestaurantController.class).getById(restaurantId)).withSelfRel();
    }

    public Link toRestaurants() {
        String restaurantUrl = linkTo(RestaurantController.class).toUri().toString();
        TemplateVariables variables = new TemplateVariables(new TemplateVariable("projection", VariableType.REQUEST_PARAM));
        return Link.of(UriTemplate.of(restaurantUrl, variables), "restaurants");
    }

    public Link toRestaurants(String rel){
        return linkTo(RestaurantController.class).withRel(rel);
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

    public Link toRestaurantPaymentMethods(Long restaurantId) {
        return linkTo(methodOn(RestaurantPaymentMethodController.class).listAll(restaurantId)).withRel("payment-methods");
    }

    public Link toDisassociatePaymentMethod(Long restaurantId, Long paymentMethodId, String rel) {
        return linkTo(methodOn(RestaurantPaymentMethodController.class).disassociate(restaurantId, paymentMethodId)).withRel(rel);
    }

    public Link associatePaymentMethod(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantPaymentMethodController.class).associate(restaurantId, null)).withRel(rel);
    }

    public Link toRestaurantUsers(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantUserController.class).getAllUsers(restaurantId)).withRel(rel);
    }

    public Link toRestaurantUsers(Long restaurantId) {
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

    public Link toPaymentMethods() {
        return linkTo(PaymentMethodController.class).withSelfRel();
    }

    public Link toPaymentMethods(String rel) {
        return linkTo(PaymentMethodController.class).withRel(rel);
    }

    public Link toCity(Long cityId) {
        return linkTo(methodOn(CityController.class).getById(cityId)).withSelfRel();
    }

    public Link toCities() {
        return linkTo(methodOn(CityController.class).listAll()).withSelfRel();
    }

    public Link toCities(String rel) {
        return linkTo(CityController.class).withRel(rel);
    }

    public Link toState(Long stateId) {
        return linkTo(methodOn(StateController.class).getById(stateId)).withSelfRel();
    }

    public Link toStates() {
        return linkTo(methodOn(StateController.class).listAll()).withSelfRel();
    }

    public Link toStates(String rel) {
        return linkTo(StateController.class).withRel(rel);
    }

    public Link toKitchen(Long kitchenId) {
        return linkTo(methodOn(KitchenController.class).getById(kitchenId)).withSelfRel();
    }

    public Link toKitchens() {
        return linkTo(KitchenController.class).withSelfRel();
    }

    public Link toKitchens(String rel) {
        return linkTo(KitchenController.class).withRel(rel);
    }

    public Link toPermissions(Long groupId, String rel) {
        return linkTo(methodOn(GroupPermissionController.class).getAll(groupId)).withRel(rel);
    }

    public Link toPermissions() {
        return linkTo(methodOn(PermissionController.class).findAll()).withSelfRel();
    }

    public Link toPermissions(String rel) {
        return linkTo(PermissionController.class).withRel(rel);
    }

    public Link toDissociatePermission(Long groupId, Long permissionId, String rel) {
        return linkTo(methodOn(GroupPermissionController.class).dissociatePermission(groupId, permissionId)).withRel(rel);
    }

    public Link toAssociatePermissions(Long groupId, String rel) {
        return linkTo(methodOn(GroupPermissionController.class).dissociatePermission(groupId, null)).withRel(rel);
    }

    public Link toAssociateGroup(Long userId, Long groupId, String rel) {
        return linkTo(methodOn(UserGroupController.class).associate(userId, groupId)).withRel(rel);
    }

    public Link toDissociateGroup(Long userId, String rel) {
        return linkTo(methodOn(UserGroupController.class).dissociate(userId, null)).withRel(rel);
    }

}
