package com.github.edurbs.datsa.api.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.dto.input.OrderInput;
import com.github.edurbs.datsa.api.dto.output.OrderOutput;
import com.github.edurbs.datsa.domain.model.Order;

@Component
public class OrderMapper implements IMapper<Order, OrderInput, OrderOutput> {

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
    public OrderOutput toOutput(Order domainModel) {
        return modelMapper.map(domainModel, OrderOutput.class);
    }

    @Override
    public Collection<OrderOutput> toOutputList(Collection<Order> domainModels) {
        return domainModels.stream().map(this::toOutput).collect(Collectors.toSet());
    }

}
