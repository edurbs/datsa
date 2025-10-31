package com.github.edurbs.datsa.api.v1.mapper;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.OrderController;
import com.github.edurbs.datsa.api.v1.dto.input.OrderInput;
import com.github.edurbs.datsa.api.v1.dto.output.OrderSummaryOutput;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class OrderSummaryMapper extends RepresentationModelAssemblerSupport<Order, OrderSummaryOutput> {

    private final ModelMapper modelMapper;
    private final LinksAdder linksAdder;
    private final MySecurity mySecurity;

    public OrderSummaryMapper(ModelMapper modelMapper, LinksAdder linksAdder, MySecurity mySecurity) {
        super(OrderController.class, OrderSummaryOutput.class);
        this.modelMapper = modelMapper;
        this.linksAdder = linksAdder;
        this.mySecurity = mySecurity;
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
        if(this.mySecurity.canSearchWithFilter()){
            orderSummaryOutput.add(linksAdder.toOrders());
        }
        if(this.mySecurity.canConsultRestaurants()){
            orderSummaryOutput.getRestaurant().add(linksAdder.toRestaurant(orderSummaryOutput.getRestaurant().getId()));
        }
        return orderSummaryOutput;
    }

}
