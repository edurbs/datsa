package com.github.edurbs.datsa.api.v2.mapper;

import com.github.edurbs.datsa.api.v2.LinksAdderV2;
import com.github.edurbs.datsa.api.v2.controller.CityControllerV2;
import com.github.edurbs.datsa.api.v2.dto.input.CityInputV2;
import com.github.edurbs.datsa.api.v2.dto.output.CityOutputV2;
import com.github.edurbs.datsa.domain.model.City;
import com.github.edurbs.datsa.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CityMapperV2 extends RepresentationModelAssemblerSupport<City, CityOutputV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdderV2 linksAdder;

    public CityMapperV2() {
        super(CityControllerV2.class, CityOutputV2.class);
    }

    public City toDomain(CityInputV2 cityInput) {
        return modelMapper.map(cityInput, City.class);
    }

    public void copyToDomain(CityInputV2 cityInput, City city) {
        city.setState(new State());
        modelMapper.map(cityInput, city);
    }

    @Override
    public @NonNull CityOutputV2 toModel(@NonNull City city) {
        CityOutputV2 cityOutput = createModelWithId(city.getId(), city);
        modelMapper.map(city, cityOutput);
        //cityOutput.getState().add(linksAdder.toState(cityOutput.getState().getId()));
        return cityOutput;
    }

    @Override
    public @NonNull CollectionModel<CityOutputV2> toCollectionModel(@NonNull Iterable<? extends City> entities) {
        return super.toCollectionModel(entities)
                // add the link to the this in the bottom
                .add(linksAdder.toCities());
    }

}
