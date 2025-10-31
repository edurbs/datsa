package com.github.edurbs.datsa.api.v1.mapper;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.OrderController;
import com.github.edurbs.datsa.api.v1.dto.input.OrderInput;
import com.github.edurbs.datsa.api.v1.dto.output.OrderOutput;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper extends RepresentationModelAssemblerSupport<Order, OrderOutput> {

    private final LinksAdder linksAdder;
    private final ModelMapper modelMapper;
    private final MySecurity mySecurity;

    public OrderMapper(Class<?> controllerClass, Class<OrderOutput> resourceType, LinksAdder linksAdder, ModelMapper modelMapper, MySecurity mySecurity) {
        super(OrderController.class, OrderOutput.class);
        this.linksAdder = linksAdder;
        this.modelMapper = modelMapper;
        this.mySecurity = mySecurity;
    }

    public Order toDomain(OrderInput inputModel) {
        return modelMapper.map(inputModel, Order.class);
    }

    public void copyToDomain(OrderInput inputModel, Order domainModel) {
        modelMapper.map(inputModel, domainModel);
    }

    @Override
    public @NonNull OrderOutput toModel(@NonNull Order domainModel) {
        OrderOutput orderOutput = createModelWithId(domainModel.getUuid(), domainModel);
        modelMapper.map(domainModel, orderOutput);

        if (this.mySecurity.canSearchWithFilter()) {
            orderOutput.add(linksAdder.toOrders());
        }
        if (mySecurity.canManageOrders(orderOutput.getUuid())) {
            if (domainModel.canBeConfirmed()) {
                orderOutput.add(linksAdder.toOrderConfirm(orderOutput.getUuid(), "confirm"));
            }
            if (domainModel.canBeCancelled()) {
                orderOutput.add(linksAdder.toOrderCancel(orderOutput.getUuid(), "cancel"));
            }
            if (domainModel.canBeDelivered()) {
                orderOutput.add(linksAdder.toOrderDelivery(orderOutput.getUuid(), "delivery"));
            }
        }
        if (this.mySecurity.canConsultRestaurants()) {
            orderOutput.getRestaurant().add(linksAdder.toRestaurant(orderOutput.getRestaurant().getId()));
            orderOutput.getOrderItems().forEach(item -> item.add(linksAdder
                    .toRestaurantProduct(orderOutput.getRestaurant().getId(), item.getProductId(), "product")));

        }
        if (this.mySecurity.canConsultUsersGroupsPermissions()) {
            orderOutput.getUser().add(linksAdder.toUser(orderOutput.getUser().getId()));

        }
        if (this.mySecurity.canConsultPaymentMethods()) {
            orderOutput.getPaymentMethod().add(linksAdder.toPaymentMethod(orderOutput.getPaymentMethod().getId()));

        }
        if (this.mySecurity.canConsultCities()) {
            orderOutput.getAddress().getCity().add(linksAdder.toCity(orderOutput.getAddress().getCity().getId()));

        }

        return orderOutput;
    }

}
