package com.github.edurbs.datsa.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.github.edurbs.datsa.api.dto.output.RestaurantOutput;
import com.github.edurbs.datsa.api.mapper.RestaurantMapper;
import com.github.edurbs.datsa.domain.exception.CityNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.exception.StateNotFoundException;
import com.github.edurbs.datsa.domain.service.RestaurantRegistryService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRegistryService restaurantRegistryService;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @GetMapping
    public List<RestaurantOutput> listAll() {
        return restaurantMapper.toOutputList(restaurantRegistryService.getAll());
    }

    @GetMapping("/{restaurantId}")
    public RestaurantOutput getById(@PathVariable Long restaurantId) {
        return restaurantMapper.toOutput(restaurantRegistryService.getById(restaurantId));
    }

    @PostMapping
    public ResponseEntity<RestaurantOutput> add(@RequestBody @Valid RestaurantInput restaurantInput) {
        try {
            var restaurant = restaurantMapper.toDomain(restaurantInput);
            var restaurantAdded = restaurantRegistryService.save(restaurant);
            var uri = URI.create("/restaurants/"+restaurantAdded.getId());
            var restaurantOutput = restaurantMapper.toOutput(restaurantAdded);
            return ResponseEntity.created(uri).body(restaurantOutput);
        } catch (CityNotFoundException | StateNotFoundException e) {
            throw new ModelValidationException(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}")
    public RestaurantOutput alter(@PathVariable Long restaurantId, @RequestBody @Valid RestaurantInput restaurantInput) {
        try {
            var restaurant = restaurantRegistryService.getById(restaurantId);
            restaurantMapper.copyToDomain(restaurantInput, restaurant);
            var restaurantAltered = restaurantRegistryService.save(restaurant);
            return restaurantMapper.toOutput(restaurantAltered);
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

    @DeleteMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivate(@PathVariable Long restaurantId) {
        restaurantRegistryService.inactivate(restaurantId);
    }


}
