package com.github.edurbs.datsa.domain.service;

import java.util.List;
import java.util.Set;

import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.exception.RestaurantNotFoundException;
import com.github.edurbs.datsa.domain.model.Product;
import com.github.edurbs.datsa.domain.model.Restaurant;
import com.github.edurbs.datsa.domain.repository.RestaurantRepository;

@Service
public class RestaurantRegistryService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenRegistryService kitchenRegistryService;

    @Autowired
    private CityRegistryService cityRegistryService;

    @Autowired
    private PaymentMethodRegistryService paymentMethodRegistryService;

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        var kitchen = restaurant.getKitchen();
        kitchen = kitchenRegistryService.getById(kitchen.getId());
        restaurant.setKitchen(kitchen);

        var address = restaurant.getAddress();
        if(address!=null){
            var city = address.getCity();
            city = cityRegistryService.getById(city.getId());
            address.setCity(city);
        }


        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant getById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    @Transactional
    public void remove(Long id) {
        if (notExists(id)) {
            throw new RestaurantNotFoundException(id);
        }
        try {
            restaurantRepository.deleteById(id);
            restaurantRepository.flush(); // delete now
        } catch (DataIntegrityViolationException e) {
            throw new ModelInUseException("Restaurant id %d in use".formatted(id));
        }
    }

    @Transactional
    public void activate(Long id) {
        var restaurant = getById(id);
        if(restaurant.isActive()) {
            throw new ModelValidationException("Restaurant id %d already active".formatted(id));
        }
        restaurant.activate();
    }

    @Transactional
    public void inactivate(Long id) {
        var restaurant = getById(id);
        if(restaurant.isInactive()) {
            throw new ModelValidationException("Restaurant id %d already inactive".formatted(id));
        }
        restaurant.inactivate();
    }

    @Transactional
    public void disassociatePaymentMethod(Long restaurantId, Long paymentMethodId){
        var restaurant = getById(restaurantId);
        var paymentMethod = paymentMethodRegistryService.getById(paymentMethodId);
        restaurant.removePaymentMethod(paymentMethod);
        // will be auto saved by JPA Transactional
    }

    @Transactional
    public void associatePaumentMethod(Long restaurantId, Long paymentMethodId){
        var restaurant = getById(restaurantId);
        var paymentMethod = paymentMethodRegistryService.getById(paymentMethodId);
        restaurant.addPaymentMethod(paymentMethod);
        // will be auto saved by JPA Transactional
    }

    public Set<Product> getAllProducts(Long restaurantId){
        var restaurant = getById(restaurantId);
        return restaurant.getProducts();
    }

    public Product getProduct(Long restaurantId, Long productId){
        var restaurant = getById(restaurantId);
        return restaurant.getProduct(productId);
    }

    private boolean exists(Long id) {
        return restaurantRepository.existsById(id);
    }

    private boolean notExists(Long id) {
        return !exists(id);
    }

    @Transactional
    public void open(Long restaurantId) {
        var restaurant = getById(restaurantId);
        if(restaurant.isOpen()){
            throw new ModelValidationException("Restaurant id %d is already opened.".formatted(restaurantId));
        }
        restaurant.open();
    }



}
