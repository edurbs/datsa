package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.ResourceUriHelper;
import com.github.edurbs.datsa.api.v1.dto.input.CityInput;
import com.github.edurbs.datsa.api.v1.dto.output.CityOutput;
import com.github.edurbs.datsa.api.v1.mapper.CityMapper;
import com.github.edurbs.datsa.domain.exception.CityNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.exception.StateNotFoundException;
import com.github.edurbs.datsa.domain.service.CityRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/cities")
public class CityController {

    @Autowired
    private CityRegistryService cityRegistryService;

    @Autowired
    private CityMapper cityMapper;

    @GetMapping
    public CollectionModel<CityOutput> listAll() {
        return cityMapper.toCollectionModel(cityRegistryService.getAll());
    }

    @GetMapping("/{cityId}")
    public CityOutput getById(@PathVariable Long cityId) {
        try {
            return cityMapper.toModel(cityRegistryService.getById(cityId));
        } catch (CityNotFoundException e) {
            throw new ModelNotFoundException(e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityOutput add(@RequestBody @Valid CityInput cityInput) {
        try {
            var city = cityMapper.toDomain(cityInput);
            var cityAdded = cityRegistryService.save(city);
            CityOutput cityOutput = cityMapper.toModel(cityAdded);
            ResourceUriHelper.addUriInResponseHeader(cityOutput.getId());
            return cityOutput;
        } catch (StateNotFoundException e) {
            throw new ModelValidationException(e.getMessage());
        }
    }

    @PutMapping("/{cityId}")
    public CityOutput alter(@PathVariable Long cityId, @RequestBody @Valid CityInput cityInput) {
        try {
            var city = cityRegistryService.getById(cityId);
            cityMapper.copyToDomain(cityInput, city);
            var alteredCity = cityRegistryService.save(city);
            return cityMapper.toModel(alteredCity);
        } catch (CityNotFoundException e) {
            throw new ModelNotFoundException(e.getMessage());
        } catch (StateNotFoundException e){
            throw new ModelValidationException(e.getMessage());
        }
    }

    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cityId) {
        cityRegistryService.remove(cityId);
    }

}
