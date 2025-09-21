package com.github.edurbs.datsa.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.RestaurantController;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantSummaryOutput;
import com.github.edurbs.datsa.domain.model.Restaurant;

@Component
public class RestaurantSummaryMapper extends RepresentationModelAssemblerSupport<Restaurant, RestaurantSummaryOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdder linksAdder;

    public RestaurantSummaryMapper() {
        super(RestaurantController.class, RestaurantSummaryOutput.class);
    }

    @Override
    public @NonNull RestaurantSummaryOutput toModel(@NonNull Restaurant entity) {
        RestaurantSummaryOutput model = createModelWithId(entity.getId(), entity);
        modelMapper.map(entity, model);
        model.getKitchen().add(linksAdder.toKitchen(entity.getKitchen().getId()));
        model.getKitchen().add(linksAdder.toKitchens());
        return model;
    }

    @Override
    public @NonNull CollectionModel<RestaurantSummaryOutput> toCollectionModel(@NonNull Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities).add(linksAdder.toRestaurants());
    }



}
