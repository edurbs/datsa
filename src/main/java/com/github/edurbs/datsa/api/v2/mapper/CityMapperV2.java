package com.github.edurbs.datsa.api.v2.mapper;

import com.github.edurbs.datsa.api.v2.LinksAdderV2;
import com.github.edurbs.datsa.api.v2.controller.CityControllerV2;
import com.github.edurbs.datsa.api.v2.dto.input.CityInputV2;
import com.github.edurbs.datsa.api.v2.dto.output.CityOutputV2;
import com.github.edurbs.datsa.domain.model.City;
import com.github.edurbs.datsa.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CityMapperV2 extends RepresentationModelAssemblerSupport<City, CityOutputV2> {

    private final ModelMapper modelMapper;

    private final LinksAdderV2 linksAdder;

    public CityMapperV2(ModelMapper modelMapper, LinksAdderV2 linksAdder) {
        super(CityControllerV2.class, CityOutputV2.class);
        this.modelMapper = modelMapper;
        this.linksAdder = linksAdder;
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
        return cityOutput;
    }

    @Override
    public @NonNull CollectionModel<CityOutputV2> toCollectionModel(@NonNull Iterable<? extends City> entities) {
        return super.toCollectionModel(entities)
                // add the link to the "this" in the bottom
                .add(linksAdder.toCities());
    }

}
