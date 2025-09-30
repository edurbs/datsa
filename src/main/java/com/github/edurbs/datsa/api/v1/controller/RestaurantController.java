package com.github.edurbs.datsa.api.v1.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.v1.dto.input.RestaurantInput;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantNameOutput;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantOutput;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantSummaryOutput;
import com.github.edurbs.datsa.api.v1.mapper.RestaurantMapper;
import com.github.edurbs.datsa.api.v1.mapper.RestaurantNameMapper;
import com.github.edurbs.datsa.api.v1.mapper.RestaurantSummaryMapper;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.domain.exception.CityNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.exception.RestaurantNotFoundException;
import com.github.edurbs.datsa.domain.exception.StateNotFoundException;
import com.github.edurbs.datsa.domain.service.RestaurantRegistryService;

@RestController
@RequestMapping("/v1/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRegistryService restaurantRegistryService;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private RestaurantSummaryMapper restaurantSummaryMapper;

    @Autowired
    private RestaurantNameMapper restaurantNameMapper;

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping
    public CollectionModel<RestaurantSummaryOutput> listAll() {
        return restaurantSummaryMapper.toCollectionModel(restaurantRegistryService.getAll());
    }

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping(params = "projection=only-name")
    public CollectionModel<RestaurantNameOutput> listAllOnlyName() {
        return restaurantNameMapper.toCollectionModel(restaurantRegistryService.getAll());
    }

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping("/{restaurantId}")
    public RestaurantOutput getById(@PathVariable Long restaurantId) {
        return restaurantMapper.toModel(restaurantRegistryService.getById(restaurantId));
    }

    @CheckSecurity.Restaurants.CanEdit
    @PostMapping
    public ResponseEntity<RestaurantOutput> add(@RequestBody @Valid RestaurantInput restaurantInput) {
        try {
            var restaurant = restaurantMapper.toDomain(restaurantInput);
            var restaurantAdded = restaurantRegistryService.save(restaurant);
            var uri = URI.create("/restaurants/" + restaurantAdded.getId());
            var restaurantOutput = restaurantMapper.toModel(restaurantAdded);
            return ResponseEntity.created(uri).body(restaurantOutput);
        } catch (CityNotFoundException | StateNotFoundException e) {
            throw new ModelValidationException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurants.CanEdit
    @PutMapping("/{restaurantId}")
    public RestaurantOutput alter(@PathVariable Long restaurantId,
            @RequestBody @Valid RestaurantInput restaurantInput) {
        try {
            var restaurant = restaurantRegistryService.getById(restaurantId);
            restaurantMapper.copyToDomain(restaurantInput, restaurant);
            var restaurantAltered = restaurantRegistryService.save(restaurant);
            return restaurantMapper.toModel(restaurantAltered);
        } catch (CityNotFoundException | StateNotFoundException e) {
            throw new ModelValidationException(e.getMessage());
        }
    }

    @CheckSecurity.Restaurants.CanEdit
    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long restaurantId) {
        restaurantRegistryService.remove(restaurantId);
    }

    @CheckSecurity.Restaurants.CanEdit
    @PutMapping("/{restaurantId}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> activate(@PathVariable Long restaurantId) {
        restaurantRegistryService.activate(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.CanEdit
    @PutMapping("/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> activations(@RequestBody List<Long> restaurantIds){
        try {
            restaurantRegistryService.activations(restaurantIds);
        } catch (RestaurantNotFoundException e) {
            throw new ModelValidationException(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.CanEdit
    @DeleteMapping("/{restaurantId}/inactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inactivate(@PathVariable Long restaurantId) {
        restaurantRegistryService.inactivate(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.CanEdit
    @DeleteMapping("/inactivations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inactivations(@RequestBody List<Long> restaurantIds){
        try {
            restaurantRegistryService.inactivations(restaurantIds);
        } catch (RestaurantNotFoundException e) {
            throw new ModelValidationException(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.CanEditAndManage
    @PutMapping("/{restaurantId}/open")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> open(@PathVariable Long restaurantId) {
        restaurantRegistryService.open(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.CanEditAndManage
    @PutMapping("/{restaurantId}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> close(@PathVariable Long restaurantId) {
        restaurantRegistryService.close(restaurantId);
        return ResponseEntity.noContent().build();
    }

}
