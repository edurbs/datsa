package com.github.edurbs.datsa.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.LinksAdder;
import com.github.edurbs.datsa.api.controller.RestaurantController;
import com.github.edurbs.datsa.api.dto.input.RestaurantInput;
import com.github.edurbs.datsa.api.dto.output.CitySummaryOutput;
import com.github.edurbs.datsa.api.dto.output.KitchenOutput;
import com.github.edurbs.datsa.api.dto.output.RestaurantOutput;
import com.github.edurbs.datsa.domain.model.City;
import com.github.edurbs.datsa.domain.model.Kitchen;
import com.github.edurbs.datsa.domain.model.Restaurant;

@Component
public class RestaurantMapper extends RepresentationModelAssemblerSupport<Restaurant, RestaurantOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdder linksAdder;

    public RestaurantMapper(){
        super(RestaurantController.class, RestaurantOutput.class);
    }

    public Restaurant toDomain(RestaurantInput restaurantInput) {
        return modelMapper.map(restaurantInput, Restaurant.class);
    }

    public void copyToDomain(RestaurantInput restaurantInput, Restaurant restaurant) {
        restaurant.setKitchen(new Kitchen());
        if(restaurant.getAddress()!=null){
            restaurant.getAddress().setCity(new City());
        }
        modelMapper.map(restaurantInput, restaurant);
    }

    @Override
    public @NonNull RestaurantOutput toModel(@NonNull Restaurant entity) {
        Long restaurantId = entity.getId();
        RestaurantOutput model = createModelWithId(restaurantId, entity);
        modelMapper.map(entity, model);
        KitchenOutput kitchen = model.getKitchen();
        kitchen.add(linksAdder.toKitchen(kitchen.getId()));
        CitySummaryOutput city = model.getAddress().getCity();
        city.add(linksAdder.toCity(city.getId()));
        model.add(linksAdder.toRestaurants());
        if(entity.canBeOpened()){
            model.add(linksAdder.toRestaurantOpen(restaurantId));
        }
        if(entity.canBeClosed()){
            model.add(linksAdder.toRestaurantClose(restaurantId));
        }
        if(entity.canBeActivated()){
            model.add(linksAdder.toRestaurantActivate(restaurantId));
        }
        if(entity.canBeInactivated()){
            model.add(linksAdder.toRestaurantInactivate(restaurantId));
        }
        model.add(linksAdder.toRestaurantPaymentMethods(restaurantId));
        model.add(linksAdder.toRestaurantUsers(restaurantId));
        return model;
    }

    @Override
    public CollectionModel<RestaurantOutput> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities).add(linksAdder.toRestaurants());
    }




}
