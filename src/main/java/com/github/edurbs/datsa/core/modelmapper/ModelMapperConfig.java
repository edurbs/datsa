package com.github.edurbs.datsa.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.edurbs.datsa.api.dto.output.AddressOutput;
import com.github.edurbs.datsa.domain.model.Address;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var mapper = new ModelMapper();
        var addressDomainToOutput = mapper.createTypeMap(Address.class, AddressOutput.class);
        addressDomainToOutput.<String>addMapping(
            domain -> domain.getCity().getState().getName(),
            (output, value) -> output.getCity().setState(value));
        return mapper;
    }
}
