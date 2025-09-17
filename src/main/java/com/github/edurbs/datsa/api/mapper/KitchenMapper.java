package com.github.edurbs.datsa.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.LinksAdder;
import com.github.edurbs.datsa.api.controller.KitchenController;
import com.github.edurbs.datsa.api.dto.input.KitchenInput;
import com.github.edurbs.datsa.api.dto.output.KitchenOutput;
import com.github.edurbs.datsa.domain.model.Kitchen;

@Component
public class KitchenMapper extends RepresentationModelAssemblerSupport<Kitchen, KitchenOutput>{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdder linksAdder;

    public KitchenMapper(){
        super(KitchenController.class, KitchenOutput.class);
    }

    public Kitchen toDomain(KitchenInput kitchenInput) {
        return modelMapper.map(kitchenInput, Kitchen.class);
    }

    @Override
    public @NonNull KitchenOutput toModel(@NonNull  Kitchen kitchen) {
        KitchenOutput kitchenOutput = createModelWithId(kitchen.getId(), kitchen);
        modelMapper.map(kitchen, kitchenOutput);
        kitchenOutput.add(linksAdder.toKitchens());
        return kitchenOutput;
    }


    public void copyToDomain(KitchenInput kitchenInput, Kitchen kitchen) {
        modelMapper.map(kitchenInput, kitchen);
    }



}
