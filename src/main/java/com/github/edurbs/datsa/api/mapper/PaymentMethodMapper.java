package com.github.edurbs.datsa.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.LinksAdder;
import com.github.edurbs.datsa.api.controller.PaymentMethodController;
import com.github.edurbs.datsa.api.dto.input.PaymentMethodInput;
import com.github.edurbs.datsa.api.dto.output.PaymentMethodOutput;
import com.github.edurbs.datsa.domain.model.PaymentMethod;

@Component
public class PaymentMethodMapper extends RepresentationModelAssemblerSupport<PaymentMethod, PaymentMethodOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdder linksAdder;

    public PaymentMethodMapper(){
        super(PaymentMethodController.class, PaymentMethodOutput.class);
    }

    public PaymentMethod toDomain(PaymentMethodInput input){
        return modelMapper.map(input, PaymentMethod.class);
    }

    public void copyToDomain(PaymentMethodInput input, PaymentMethod domain){
        modelMapper.map(input, domain);
    }

    @Override
    public @NonNull PaymentMethodOutput toModel(@NonNull PaymentMethod entity){
        PaymentMethodOutput output = createModelWithId(entity.getId(), entity);
        modelMapper.map(entity, output);
        //output.add(linksAdder.toPaymentMethods());
        return output;
    }


}
