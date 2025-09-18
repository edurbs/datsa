package com.github.edurbs.datsa.api.controller;

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

import com.github.edurbs.datsa.api.dto.input.RestaurantInput;
import com.github.edurbs.datsa.api.dto.output.RestaurantNameOutput;
import com.github.edurbs.datsa.api.dto.output.RestaurantOutput;
import com.github.edurbs.datsa.api.dto.output.RestaurantSummaryOutput;
import com.github.edurbs.datsa.api.mapper.RestaurantMapper;
import com.github.edurbs.datsa.api.mapper.RestaurantNameMapper;
import com.github.edurbs.datsa.api.mapper.RestaurantSummaryMapper;
import com.github.edurbs.datsa.domain.exception.CityNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.exception.RestaurantNotFoundException;
import com.github.edurbs.datsa.domain.exception.StateNotFoundException;
import com.github.edurbs.datsa.domain.service.RestaurantRegistryService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRegistryService restaurantRegistryService;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private RestaurantSummaryMapper restaurantSummaryMapper;

    @Autowired
    private RestaurantNameMapper restaurantNameMapper;

    @GetMapping
    public CollectionModel<RestaurantSummaryOutput> listAll() {
        return restaurantSummaryMapper.toCollectionModel(restaurantRegistryService.getAll());
    }

    @GetMapping(params = "projection=only-name")
    public CollectionModel<RestaurantNameOutput> listAllOnlyName() {
        return restaurantNameMapper.toCollectionModel(restaurantRegistryService.getAll());
    }

    @GetMapping("/{restaurantId}")
    public RestaurantOutput getById(@PathVariable Long restaurantId) {
        return restaurantMapper.toModel(restaurantRegistryService.getById(restaurantId));
    }

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

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long restaurantId) {
        restaurantRegistryService.remove(restaurantId);
    }

    @PutMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable Long restaurantId) {
        restaurantRegistryService.activate(restaurantId);
    }

    @PutMapping("/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activations(@RequestBody List<Long> restaurantIds){
        try {
            restaurantRegistryService.activations(restaurantIds);
        } catch (RestaurantNotFoundException e) {
            throw new ModelValidationException(e.getMessage());
        }
    }

    @DeleteMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivate(@PathVariable Long restaurantId) {
        restaurantRegistryService.inactivate(restaurantId);
    }

    @DeleteMapping("/inactivations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivations(@RequestBody List<Long> restaurantIds){
        try {
            restaurantRegistryService.inactivations(restaurantIds);
        } catch (RestaurantNotFoundException e) {
            throw new ModelValidationException(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}/opening")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void open(@PathVariable Long restaurantId) {
        restaurantRegistryService.open(restaurantId);
    }

    @PutMapping("/{restaurantId}/closing")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void close(@PathVariable Long restaurantId) {
        restaurantRegistryService.close(restaurantId);
    }

}
