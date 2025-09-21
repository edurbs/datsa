package com.github.edurbs.datsa.api.v2.controller;

import com.github.edurbs.datsa.api.ResourceUriHelper;
import com.github.edurbs.datsa.api.v2.dto.input.CityInputV2;
import com.github.edurbs.datsa.api.v2.dto.output.CityOutputV2;
import com.github.edurbs.datsa.api.v2.mapper.CityMapperV2;
import com.github.edurbs.datsa.core.web.MyMediaTypes;
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
@RequestMapping("/cities")
public class CityControllerV2 {

    @Autowired
    private CityRegistryService cityRegistryService;

    @Autowired
    private CityMapperV2 cityMapper;

    @GetMapping(produces = MyMediaTypes.V2_APPLICATION_JSON_VALUE)
    public CollectionModel<CityOutputV2> listAll() {
        return cityMapper.toCollectionModel(cityRegistryService.getAll());
    }

    @GetMapping(path = "/{cityId}", produces = MyMediaTypes.V2_APPLICATION_JSON_VALUE)
    public CityOutputV2 getById(@PathVariable Long cityId) {
        try {
            return cityMapper.toModel(cityRegistryService.getById(cityId));
        } catch (CityNotFoundException e) {
            throw new ModelNotFoundException(e.getMessage());
        }
    }

    @PostMapping(produces = MyMediaTypes.V2_APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CityOutputV2 add(@RequestBody @Valid CityInputV2 cityInput) {
        try {
            var city = cityMapper.toDomain(cityInput);
            var cityAdded = cityRegistryService.save(city);
            CityOutputV2 cityOutput = cityMapper.toModel(cityAdded);
            ResourceUriHelper.addUriInResponseHeader(cityOutput.getCityId());
            return cityOutput;
        } catch (StateNotFoundException e) {
            throw new ModelValidationException(e.getMessage());
        }
    }

    @PutMapping(path = "/{cityId}", produces = MyMediaTypes.V2_APPLICATION_JSON_VALUE)
    public CityOutputV2 alter(@PathVariable Long cityId, @RequestBody @Valid CityInputV2 cityInput) {
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

    @DeleteMapping(path = "/{cityId}", produces = MyMediaTypes.V2_APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cityId) {
        cityRegistryService.remove(cityId);
    }

}
