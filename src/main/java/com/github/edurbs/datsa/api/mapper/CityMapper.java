package com.github.edurbs.datsa.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.controller.CityController;
import com.github.edurbs.datsa.api.controller.StateController;
import com.github.edurbs.datsa.api.dto.input.CityInput;
import com.github.edurbs.datsa.api.dto.output.CityOutput;
import com.github.edurbs.datsa.domain.model.City;
import com.github.edurbs.datsa.domain.model.State;

@Component
// public class CityMapper implements IMapper<City, CityInput, CityOutput> {
public class CityMapper extends RepresentationModelAssemblerSupport<City, CityOutput> {

    @Autowired
    private ModelMapper modelMapper;

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
        //CityOutput cityOutput = modelMapper.map(city, CityOutput.class);
        modelMapper.map(city, cityOutput);
        addHateOas(cityOutput);
        return cityOutput;
    }

    // public List<CityOutput> toOutputList(Collection<City> cities) {
    //     return cities.stream()
    //             .map(this::toOutput)
    //             .toList();
    // }

    @Override
    public @NonNull CollectionModel<CityOutput> toCollectionModel(@NonNull Iterable<? extends City> entities) {
        return super.toCollectionModel(entities)
            // add the link to the this in the bottom
            .add(WebMvcLinkBuilder.linkTo(getControllerClass()).withSelfRel());
    }

    private void addHateOas(CityOutput cityOutput) {
        // cityOutput.add(
        //     WebMvcLinkBuilder.linkTo(
        //         WebMvcLinkBuilder.methodOn(CityController.class).getById(cityOutput.getId())
        //     ).withSelfRel()
        // );
        cityOutput.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CityController.class).listAll()
            ).withRel("cities")
        );

        cityOutput.getState().add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(StateController.class).getById(cityOutput.getState().getId())
            ).withSelfRel());
    }

}

