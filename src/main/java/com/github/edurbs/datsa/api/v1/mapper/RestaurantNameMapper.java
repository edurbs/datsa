package com.github.edurbs.datsa.api.v1.mapper;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.RestaurantController;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantNameOutput;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class RestaurantNameMapper extends RepresentationModelAssemblerSupport<Restaurant, RestaurantNameOutput> {

    private final ModelMapper modelMapper;
    private final LinksAdder linksAdder;
    private final MySecurity mySecurity;

    public RestaurantNameMapper(ModelMapper modelMapper, LinksAdder linksAdder, MySecurity mySecurity) {
        super(RestaurantController.class, RestaurantNameOutput.class);
        this.modelMapper = modelMapper;
        this.linksAdder = linksAdder;
        this.mySecurity = mySecurity;
    }

    @Override
    public @NonNull RestaurantNameOutput toModel(@NonNull Restaurant entity) {
        RestaurantNameOutput output = createModelWithId(entity.getId(), entity);
        modelMapper.map(entity, output);
        if(this.mySecurity.canConsultRestaurants()){
            output.add(linksAdder.toRestaurants());
        }
        return output;
    }

    @Override
    public @NonNull CollectionModel<RestaurantNameOutput> toCollectionModel(@NonNull Iterable<? extends Restaurant> entities) {
        CollectionModel<RestaurantNameOutput> collectionModel = super.toCollectionModel(entities);
        if(this.mySecurity.canConsultRestaurants()){
            collectionModel.add(linksAdder.toRestaurants());
        }
        return collectionModel;
    }



}
