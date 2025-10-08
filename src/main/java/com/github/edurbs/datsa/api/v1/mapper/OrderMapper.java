package com.github.edurbs.datsa.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.OrderController;
import com.github.edurbs.datsa.api.v1.dto.input.OrderInput;
import com.github.edurbs.datsa.api.v1.dto.output.OrderOutput;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.Order;

@Component
public class OrderMapper extends RepresentationModelAssemblerSupport<Order, OrderOutput> {

    @Autowired
    private LinksAdder linksAdder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MySecurity mySecurity;

    public OrderMapper() {
        super(OrderController.class, OrderOutput.class);
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

        orderOutput.add(linksAdder.toOrders());
        if(mySecurity.canManageOrders(orderOutput.getUuid())){
            if(domainModel.canBeConfirmed()){
                orderOutput.add(linksAdder.toOrderConfirm(orderOutput.getUuid(), "confirm"));
            }
            if(domainModel.canBeCancelled()){
                orderOutput.add(linksAdder.toOrderCancel(orderOutput.getUuid(), "cancel"));
            }
            if(domainModel.canBeDelivered()){
                orderOutput.add(linksAdder.toOrderDelivery(orderOutput.getUuid(), "delivery"));
            }
        }
        orderOutput.getRestaurant().add(linksAdder.toRestaurant(orderOutput.getRestaurant().getId()));
        orderOutput.getUser().add(linksAdder.toUser(orderOutput.getUser().getId()));
        orderOutput.getPaymentMethod().add(linksAdder.toPaymentMethod(orderOutput.getPaymentMethod().getId()));
        orderOutput.getAddress().getCity().add(linksAdder.toCity(orderOutput.getAddress().getCity().getId()));
        orderOutput.getOrderItems().forEach(item ->
            item.add(linksAdder.toRestaurantProduct(orderOutput.getRestaurant().getId(), item.getProductId(), "product"))
        );
        return orderOutput;
    }

}
