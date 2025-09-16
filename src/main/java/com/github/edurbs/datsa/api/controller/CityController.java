package com.github.edurbs.datsa.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.ResourceUriHelper;
import com.github.edurbs.datsa.api.dto.input.CityInput;
import com.github.edurbs.datsa.api.dto.output.CityOutput;
import com.github.edurbs.datsa.api.mapper.CityMapper;
import com.github.edurbs.datsa.domain.exception.CityNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.exception.StateNotFoundException;
import com.github.edurbs.datsa.domain.service.CityRegistryService;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityRegistryService cityRegistryService;

    @Autowired
    private CityMapper cityMapper;

    @GetMapping
    public List<CityOutput> listAll() {
        return cityMapper.toOutputList(cityRegistryService.getAll());
    }

    @GetMapping("/{cityId}")
    public CityOutput getById(@PathVariable Long cityId) {
        try {
            CityOutput cityOutput = cityMapper.toOutput(cityRegistryService.getById(cityId));
            cityOutput.add(
                WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(CityController.class).getById(cityId)
                ).withSelfRel()
            );
            cityOutput.add(
                WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(CityController.class).listAll()
                ).withRel("cities")
            );

            cityOutput.getState().add(
                WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(StateController.class).getById(cityOutput.getState().getId())
                ).withSelfRel());
            return cityOutput;
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
            CityOutput cityOutput = cityMapper.toOutput(cityAdded);
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
            return cityMapper.toOutput(alteredCity);
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
