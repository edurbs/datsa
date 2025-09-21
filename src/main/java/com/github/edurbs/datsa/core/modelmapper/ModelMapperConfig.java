package com.github.edurbs.datsa.core.modelmapper;

import com.github.edurbs.datsa.api.v2.dto.input.CityInputV2;
import com.github.edurbs.datsa.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.edurbs.datsa.api.v1.dto.input.OrderItemInput;
import com.github.edurbs.datsa.api.v1.dto.output.AddressOutput;
import com.github.edurbs.datsa.domain.model.Address;
import com.github.edurbs.datsa.domain.model.OrderItem;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var mapper = new ModelMapper();

        var addressDomainToOutput = mapper.createTypeMap(Address.class, AddressOutput.class);
        addressDomainToOutput.<String>addMapping(
            domain -> domain.getCity().getState().getName(),
            (output, value) -> output.getCity().setState(value));

        mapper.createTypeMap(OrderItemInput.class, OrderItem.class)
            .addMappings(m -> m.skip(OrderItem::setId));

        mapper.createTypeMap(CityInputV2.class, City.class)
            .addMappings(m -> m.skip(City::setId));

        return mapper;
    }
}
