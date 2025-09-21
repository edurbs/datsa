package com.github.edurbs.datsa.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.RestaurantController;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantNameOutput;
import com.github.edurbs.datsa.domain.model.Restaurant;

@Component
public class RestaurantNameMapper extends RepresentationModelAssemblerSupport<Restaurant, RestaurantNameOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdder linksAdder;

    public RestaurantNameMapper(){
        super(RestaurantController.class, RestaurantNameOutput.class);
    }

    @Override
    public @NonNull RestaurantNameOutput toModel(@NonNull Restaurant entity) {
        RestaurantNameOutput output = createModelWithId(entity.getId(), entity);
        modelMapper.map(entity, output);
        output.add(linksAdder.toRestaurants());
        return output;
    }

    @Override
    public CollectionModel<RestaurantNameOutput> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities).add(linksAdder.toRestaurants());
    }



}
