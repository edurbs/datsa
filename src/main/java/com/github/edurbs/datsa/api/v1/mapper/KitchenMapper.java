package com.github.edurbs.datsa.api.v1.mapper;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.KitchenController;
import com.github.edurbs.datsa.api.v1.dto.input.KitchenInput;
import com.github.edurbs.datsa.api.v1.dto.output.KitchenOutput;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class KitchenMapper extends RepresentationModelAssemblerSupport<Kitchen, KitchenOutput>{

    private final ModelMapper modelMapper;

    private final LinksAdder linksAdder;

    private final MySecurity mySecurity;

    public KitchenMapper(ModelMapper modelMapper, LinksAdder linksAdder, MySecurity mySecurity) {
        super(KitchenController.class, KitchenOutput.class);
        this.modelMapper = modelMapper;
        this.linksAdder = linksAdder;
        this.mySecurity = mySecurity;
    }

    public Kitchen toDomain(KitchenInput kitchenInput) {
        return modelMapper.map(kitchenInput, Kitchen.class);
    }

    @Override
    public @NonNull KitchenOutput toModel(@NonNull  Kitchen kitchen) {
        KitchenOutput kitchenOutput = createModelWithId(kitchen.getId(), kitchen);
        modelMapper.map(kitchen, kitchenOutput);
        if(this.mySecurity.canConsultKitchens()){
            kitchenOutput.add(linksAdder.toKitchens());

        }
        return kitchenOutput;
    }


    public void copyToDomain(KitchenInput kitchenInput, Kitchen kitchen) {
        modelMapper.map(kitchenInput, kitchen);
    }



}
