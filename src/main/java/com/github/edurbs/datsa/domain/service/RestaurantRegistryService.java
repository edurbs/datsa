package com.github.edurbs.datsa.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.Restaurant;
import com.github.edurbs.datsa.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistryService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenRegistryService kitchenRegistryService;

    public Restaurant save(Restaurant restaurant) {
        if(restaurant.getKitchen() == null) {
            throw new ModelNotFoundException("Kitchen not informed");
        }
        var kitchen = restaurant.getKitchen();
        if(kitchen.getId() == null) {
            throw new ModelNotFoundException("Kitchen id not informed");
        }
        var kitchenId = kitchen.getId();
        var kitchenFound = kitchenRegistryService.getById(kitchenId);
        restaurant.setKitchen(kitchenFound);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant getById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Restaurant id %d does not exists".formatted(id)));
    }

    @Transactional
    public void remove(Long id) {
        if (exists(id)) {
            try {
                restaurantRepository.deleteById(id);
                restaurantRepository.flush();
            } catch (DataIntegrityViolationException e) {
                throw new ModelInUseException("Restaurant id %d in use".formatted(id));
            }
        } else {
            throw new ModelNotFoundException("Restaurant id %d does not exists".formatted(id));
        }
    }

    private boolean exists(Long id) {
        return restaurantRepository.existsById(id);
    }

}
