package com.github.edurbs.datsa.api.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.dto.input.OrderInput;
import com.github.edurbs.datsa.api.dto.output.OrderSummaryOutput;
import com.github.edurbs.datsa.domain.model.Order;

@Component
public class OrderSummaryMapper implements IMapper<Order, OrderInput, OrderSummaryOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Order toDomain(OrderInput inputModel) {
        return modelMapper.map(inputModel, Order.class);
    }

    @Override
    public void copyToDomain(OrderInput inputModel, Order domainModel) {
        modelMapper.map(inputModel, domainModel);
    }

    @Override
    public OrderSummaryOutput toOutput(Order domainModel) {
        return modelMapper.map(domainModel, OrderSummaryOutput.class);
    }

    @Override
    public Collection<OrderSummaryOutput> toOutputList(Collection<Order> domainModels) {
        return domainModels.stream().map(this::toOutput).collect(Collectors.toSet());
    }

}
