package com.github.edurbs.datsa.api.mapper;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.dto.input.KitchenInput;
import com.github.edurbs.datsa.api.dto.output.KitchenOutput;
import com.github.edurbs.datsa.domain.model.Kitchen;

@Component
public class KitchenMapper implements IMapper<Kitchen, KitchenInput, KitchenOutput>{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Kitchen toDomain(KitchenInput kitchenInput) {
        return modelMapper.map(kitchenInput, Kitchen.class);
    }

    @Override
    public KitchenOutput toOutput(Kitchen kitchen) {
        return modelMapper.map(kitchen, KitchenOutput.class);
    }

    @Override
    public void copyToDomain(KitchenInput kitchenInput, Kitchen kitchen) {
        modelMapper.map(kitchenInput, kitchen);
    }

    @Override
    public Collection<KitchenOutput> toOutputList(Collection<Kitchen> kitchens) {
        return kitchens.stream()
                .map(this::toOutput)
                .toList();
    }

}
