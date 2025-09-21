package com.github.edurbs.datsa.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.OrderController;
import com.github.edurbs.datsa.api.v1.dto.input.OrderInput;
import com.github.edurbs.datsa.api.v1.dto.output.OrderSummaryOutput;
import com.github.edurbs.datsa.domain.model.Order;

@Component
public class OrderSummaryMapper extends RepresentationModelAssemblerSupport<Order, OrderSummaryOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdder linksAdder;

    public OrderSummaryMapper() {
        super(OrderController.class, OrderSummaryOutput.class);
    }

    public Order toDomain(OrderInput inputModel) {
        return modelMapper.map(inputModel, Order.class);
    }

    public void copyToDomain(OrderInput inputModel, Order domainModel) {
        modelMapper.map(inputModel, domainModel);
    }

    @Override
    public @NonNull OrderSummaryOutput toModel(@NonNull Order domainModel) {
        OrderSummaryOutput orderSummaryOutput = createModelWithId(domainModel.getId(), domainModel);
        modelMapper.map(domainModel, orderSummaryOutput);
        orderSummaryOutput.add(linksAdder.toOrders());
        orderSummaryOutput.getRestaurant().add(linksAdder.toRestaurant(orderSummaryOutput.getRestaurant().getId()));
        return orderSummaryOutput;
    }

}
