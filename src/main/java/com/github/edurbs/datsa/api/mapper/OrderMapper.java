package com.github.edurbs.datsa.api.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.controller.CityController;
import com.github.edurbs.datsa.api.controller.OrderController;
import com.github.edurbs.datsa.api.controller.PaymentMethodController;
import com.github.edurbs.datsa.api.controller.RestaurantController;
import com.github.edurbs.datsa.api.controller.RestaurantProductController;
import com.github.edurbs.datsa.api.controller.UserController;
import com.github.edurbs.datsa.api.dto.input.OrderInput;
import com.github.edurbs.datsa.api.dto.output.OrderOutput;
import com.github.edurbs.datsa.domain.model.Order;

@Component
public class OrderMapper extends RepresentationModelAssemblerSupport<Order, OrderOutput> {

    @Autowired
    private ModelMapper modelMapper;

    public OrderMapper(){
        super(OrderController.class, OrderOutput.class);
    }


    public Order toDomain(OrderInput inputModel) {
        return modelMapper.map(inputModel, Order.class);
    }


    public void copyToDomain(OrderInput inputModel, Order domainModel) {
        modelMapper.map(inputModel, domainModel);
    }

    @Override
    public OrderOutput toModel(Order domainModel) {
        OrderOutput orderOutput = createModelWithId(domainModel.getId(), domainModel);
        modelMapper.map(domainModel, orderOutput);
        orderOutput.getRestaurant().add(linkTo(
                methodOn(RestaurantController.class).getById(orderOutput.getRestaurant().getId())
            )
            .withSelfRel()
        );
        orderOutput.getUser().add((linkTo(
            methodOn(UserController.class).getOne(orderOutput.getUser().getId())
        )).withSelfRel());
        orderOutput.getPaymentMethod().add(linkTo(
                methodOn(PaymentMethodController.class).getById(orderOutput.getPaymentMethod().getId(),null)
        ).withSelfRel());
        orderOutput.getAddress().getCity().add(linkTo(
            methodOn(CityController.class).getById(orderOutput.getAddress().getCity().getId())
        ).withSelfRel());
        orderOutput.getOrderItems().forEach(item -> {
            item.add(linkTo(methodOn(RestaurantProductController.class).getOne(orderOutput.getRestaurant().getId(), item.getProductId()))
            .withRel("product"));
        });
        return orderOutput;
    }


}
