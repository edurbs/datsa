package com.github.edurbs.datsa.api.mapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
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

        //orderOutput.add(linkTo(OrderController.class).withRel("orders"));
        TemplateVariables pageVariables = new TemplateVariables(
            new TemplateVariable("page", VariableType.REQUEST_PARAM),
            new TemplateVariable("size", VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", VariableType.REQUEST_PARAM)
        );
        String orderUrl = linkTo(OrderController.class).toUri().toString();
        orderOutput.add(Link.of(UriTemplate.of(orderUrl, pageVariables), "orders"));

        orderOutput.getRestaurant()
                .add(linkTo(methodOn(RestaurantController.class).getById(orderOutput.getRestaurant().getId()))
                        .withSelfRel());
        orderOutput.getUser()
                .add((linkTo(methodOn(UserController.class).getOne(orderOutput.getUser().getId()))).withSelfRel());
        orderOutput.getPaymentMethod().add(
                linkTo(methodOn(PaymentMethodController.class).getById(orderOutput.getPaymentMethod().getId(), null))
                        .withSelfRel());
        orderOutput.getAddress().getCity()
                .add(linkTo(methodOn(CityController.class).getById(orderOutput.getAddress().getCity().getId()))
                        .withSelfRel());
        orderOutput.getOrderItems().forEach(item -> {
            item.add(linkTo(methodOn(RestaurantProductController.class).getOne(orderOutput.getRestaurant().getId(),
                    item.getProductId())).withRel("product"));
        });
        return orderOutput;
    }

}
