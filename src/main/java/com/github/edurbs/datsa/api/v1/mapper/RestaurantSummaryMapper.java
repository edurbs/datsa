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
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.Restaurant;

@Component
public class RestaurantSummaryMapper extends RepresentationModelAssemblerSupport<Restaurant, RestaurantSummaryOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdder linksAdder;

    @Autowired
    private MySecurity mySecurity;

    public RestaurantSummaryMapper() {
        super(RestaurantController.class, RestaurantSummaryOutput.class);
    }

    @Override
    public @NonNull RestaurantSummaryOutput toModel(@NonNull Restaurant entity) {
        RestaurantSummaryOutput model = createModelWithId(entity.getId(), entity);
        modelMapper.map(entity, model);
        if(this.mySecurity.canConsultRestaurants()){
            model.add(linksAdder.toRestaurants("restaurants"));
        }
        if(this.mySecurity.canConsultKitchens()){
            model.getKitchen().add(linksAdder.toKitchen(entity.getKitchen().getId()));
        }

        return model;
    }

    @Override
    public @NonNull CollectionModel<RestaurantSummaryOutput> toCollectionModel(@NonNull Iterable<? extends Restaurant> entities) {
        CollectionModel<RestaurantSummaryOutput> collectionModel = super.toCollectionModel(entities);
        if(this.mySecurity.canConsultRestaurants()){
            collectionModel.add(linksAdder.toRestaurants());
        }
        return collectionModel;
    }



}
