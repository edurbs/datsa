package com.github.edurbs.datsa.api.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.controller.OrderController;
import com.github.edurbs.datsa.api.controller.RestaurantController;
import com.github.edurbs.datsa.api.dto.input.OrderInput;
import com.github.edurbs.datsa.api.dto.output.OrderSummaryOutput;
import com.github.edurbs.datsa.domain.model.Order;

@Component
public class OrderSummaryMapper extends RepresentationModelAssemblerSupport<Order, OrderSummaryOutput> {

    @Autowired
    private ModelMapper modelMapper;

    public OrderSummaryMapper(){
        super(OrderController.class, OrderSummaryOutput.class);
    }

    public Order toDomain(OrderInput inputModel) {
        return modelMapper.map(inputModel, Order.class);
    }

    public void copyToDomain(OrderInput inputModel, Order domainModel) {
        modelMapper.map(inputModel, domainModel);
    }

    @Override
    public OrderSummaryOutput toModel(Order domainModel) {
        OrderSummaryOutput orderSummaryOutput = createModelWithId(domainModel.getId(), domainModel);
        modelMapper.map(domainModel, orderSummaryOutput);
        orderSummaryOutput.add(linkTo(OrderController.class).withRel("orders"));
        orderSummaryOutput.getRestaurant().add(linkTo(methodOn(RestaurantController.class)
            .getById(orderSummaryOutput.getRestaurant().getId())).withSelfRel());
        return orderSummaryOutput;
    }


}
