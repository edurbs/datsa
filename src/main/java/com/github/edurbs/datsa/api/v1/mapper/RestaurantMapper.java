package com.github.edurbs.datsa.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.RestaurantController;
import com.github.edurbs.datsa.api.v1.dto.input.RestaurantInput;
import com.github.edurbs.datsa.api.v1.dto.output.CitySummaryOutput;
import com.github.edurbs.datsa.api.v1.dto.output.KitchenOutput;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantOutput;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.City;
import com.github.edurbs.datsa.domain.model.Kitchen;
import com.github.edurbs.datsa.domain.model.Restaurant;

@Component
public class RestaurantMapper extends RepresentationModelAssemblerSupport<Restaurant, RestaurantOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdder linksAdder;

    @Autowired
    private MySecurity mySecurity;

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
        if(this.mySecurity.canConsultKitchens()){
            KitchenOutput kitchen = model.getKitchen();
            kitchen.add(linksAdder.toKitchen(kitchen.getId()));
        }
        if(this.mySecurity.canConsultCities() && model.getAddress()!=null) {
            CitySummaryOutput city = model.getAddress().getCity();
            city.add(linksAdder.toCity(city.getId()));

        }
        if(mySecurity.canConsultRestaurants()){
            model.add(linksAdder.toRestaurants());
            model.add(linksAdder.toRestaurantPaymentMethods(restaurantId));
            model.add(linksAdder.toProducts(restaurantId, "products"));
        }
        if(mySecurity.canEditAndManageRestaurant(restaurantId)){
            if(entity.canBeOpened()){
                model.add(linksAdder.toRestaurantOpen(restaurantId));
            }
            if(entity.canBeClosed()){
                model.add(linksAdder.toRestaurantClose(restaurantId));
            }
        }
        if(mySecurity.canEditRestaurants()){
            if(entity.canBeActivated()){
                model.add(linksAdder.toRestaurantActivate(restaurantId));
            }
            if(entity.canBeInactivated()){
                model.add(linksAdder.toRestaurantInactivate(restaurantId));
            }
            model.add(linksAdder.toRestaurantUsers(restaurantId, "users"));
        }

        return model;
    }

    @Override
    public CollectionModel<RestaurantOutput> toCollectionModel(Iterable<? extends Restaurant> entities) {
        CollectionModel<RestaurantOutput> collectionModel = super.toCollectionModel(entities);
        if(this.mySecurity.canConsultRestaurants()){
            collectionModel.add(linksAdder.toRestaurants());
        }
        return collectionModel;
    }




}
