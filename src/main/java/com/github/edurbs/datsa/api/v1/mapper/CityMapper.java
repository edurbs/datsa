package com.github.edurbs.datsa.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.CityController;
import com.github.edurbs.datsa.api.v1.dto.input.CityInput;
import com.github.edurbs.datsa.api.v1.dto.output.CityOutput;
import com.github.edurbs.datsa.domain.model.City;
import com.github.edurbs.datsa.domain.model.State;

@Component
public class CityMapper extends RepresentationModelAssemblerSupport<City, CityOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdder linksAdder;

    public CityMapper() {
        super(CityController.class, CityOutput.class);
    }

    public City toDomain(CityInput cityInput) {
        return modelMapper.map(cityInput, City.class);
    }

    public void copyToDomain(CityInput cityInput, City city) {
        city.setState(new State());
        modelMapper.map(cityInput, city);
    }

    @Override
    public @NonNull CityOutput toModel(@NonNull City city) {
        CityOutput cityOutput = createModelWithId(city.getId(), city);
        modelMapper.map(city, cityOutput);
        cityOutput.getState().add(linksAdder.toState(cityOutput.getState().getId()));
        return cityOutput;
    }

    @Override
    public @NonNull CollectionModel<CityOutput> toCollectionModel(@NonNull Iterable<? extends City> entities) {
        return super.toCollectionModel(entities)
                // add the link to the this in the bottom
                .add(linksAdder.toCities());
    }

}
