package com.github.edurbs.datsa.api.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.dto.input.KitchenInput;
import com.github.edurbs.datsa.api.dto.output.KitchenOutput;
import com.github.edurbs.datsa.domain.model.Kitchen;

@Component
public class KitchenMapper {
    private final ModelMapper modelMapper;

    public KitchenMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Kitchen toDomain(KitchenInput kitchenInput) {
        return modelMapper.map(kitchenInput, Kitchen.class);
    }

    public KitchenOutput toOutput(Kitchen kitchen) {
        return modelMapper.map(kitchen, KitchenOutput.class);
    }

    public void copyToDomain(KitchenInput kitchenInput, Kitchen kitchen) {
        modelMapper.map(kitchenInput, kitchen);
    }

    public List<KitchenOutput> toOutputList(List<Kitchen> kitchens) {
        return kitchens.stream()
                .map(this::toOutput)
                .toList();
    }
}
