package com.github.edurbs.datsa.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.github.edurbs.datsa.domain.model.City;
import com.github.edurbs.datsa.domain.service.CityRegistryService;

@RestController
@RequestMapping("/cities")
public class CityController {
    private static final String CITY_URL = "/cities";

    @Autowired
    private CityRegistryService cityRegistryService;

    @GetMapping
    public List<City> listAll() {
        return cityRegistryService.getAll();
    }

    @GetMapping("/{cityId}")
    public City getById(@PathVariable Long cityId) {
        return cityRegistryService.getById(cityId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public City add(@RequestBody @Valid City city) {
        return cityRegistryService.save(city);
    }

    @PutMapping("/{cityId}")
    public City alter(@PathVariable Long cityId, @RequestBody @Valid City city) {
        var alteredCity = cityRegistryService.getById(cityId);
        BeanUtils.copyProperties(city, alteredCity, "id");
        return cityRegistryService.save(alteredCity);
    }

    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cityId) {
        cityRegistryService.remove(cityId);
    }
}
