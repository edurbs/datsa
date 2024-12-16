package com.github.edurbs.datsa.api.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.dto.input.CityInput;
import com.github.edurbs.datsa.api.dto.output.CityOutput;
import com.github.edurbs.datsa.domain.model.City;
import com.github.edurbs.datsa.domain.model.State;

@Component
public class CityMapper {

    @Autowired
    private ModelMapper modelMapper;

    public City toDomain(CityInput cityInput) {
        return modelMapper.map(cityInput, City.class);
    }

    public void copyToDomain(CityInput cityInput, City city) {
        city.setState(new State());
        modelMapper.map(cityInput, city);
    }

    public CityOutput toOutput(City city) {
        return modelMapper.map(city, CityOutput.class);
    }

    public List<CityOutput> toOutputList(List<City> cities) {
        return cities.stream()
                .map(this::toOutput)
                .toList();
    }

}

