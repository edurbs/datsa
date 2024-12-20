package com.github.edurbs.datsa.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.github.edurbs.datsa.api.dto.input.CityInput;
import com.github.edurbs.datsa.api.dto.output.CityOutput;
import com.github.edurbs.datsa.api.mapper.CityMapper;
import com.github.edurbs.datsa.domain.service.CityRegistryService;

@RestController
@RequestMapping("/cities")
public class CityController {
    private static final String CITY_URL = "/cities";

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
        return cityMapper.toOutput(cityRegistryService.getById(cityId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityOutput add(@RequestBody @Valid CityInput cityInput) {
        var city = cityMapper.toDomain(cityInput);
        var cityAdded = cityRegistryService.save(city);
        return cityMapper.toOutput(cityAdded);
    }

    @PutMapping("/{cityId}")
    public CityOutput alter(@PathVariable Long cityId, @RequestBody @Valid CityInput cityInput) {
        var city = cityRegistryService.getById(cityId);
        cityMapper.copyToDomain(cityInput, city);
        var alteredCity = cityRegistryService.save(city);
        return cityMapper.toOutput(alteredCity);
    }

    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cityId) {
        cityRegistryService.remove(cityId);
    }
}
