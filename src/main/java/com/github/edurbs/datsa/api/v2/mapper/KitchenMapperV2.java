package com.github.edurbs.datsa.api.v2.mapper;

import com.github.edurbs.datsa.api.v2.LinksAdderV2;
import com.github.edurbs.datsa.api.v2.controller.KitchenControllerV2;
import com.github.edurbs.datsa.api.v2.dto.input.KitchenInputV2;
import com.github.edurbs.datsa.api.v2.dto.output.KitchenOutputV2;
import com.github.edurbs.datsa.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class KitchenMapperV2 extends RepresentationModelAssemblerSupport<Kitchen, KitchenOutputV2>{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdderV2 linksAdder;

    public KitchenMapperV2(){
        super(KitchenControllerV2.class, KitchenOutputV2.class);
    }

    public Kitchen toDomain(KitchenInputV2 kitchenInput) {
        return modelMapper.map(kitchenInput, Kitchen.class);
    }

    @Override
    public @NonNull KitchenOutputV2 toModel(@NonNull  Kitchen kitchen) {
        KitchenOutputV2 kitchenOutput = createModelWithId(kitchen.getId(), kitchen);
        modelMapper.map(kitchen, kitchenOutput);
        kitchenOutput.add(linksAdder.toKitchens());
        return kitchenOutput;
    }


    public void copyToDomain(KitchenInputV2 kitchenInput, Kitchen kitchen) {
        modelMapper.map(kitchenInput, kitchen);
    }



}
