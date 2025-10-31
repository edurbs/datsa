package com.github.edurbs.datsa.api.v1.mapper;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.PaymentMethodController;
import com.github.edurbs.datsa.api.v1.dto.input.PaymentMethodInput;
import com.github.edurbs.datsa.api.v1.dto.output.PaymentMethodOutput;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.PaymentMethod;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodMapper extends RepresentationModelAssemblerSupport<PaymentMethod, PaymentMethodOutput> {

    private final ModelMapper modelMapper;
    private final LinksAdder linksAdder;
    private final MySecurity mySecurity;

    public PaymentMethodMapper(ModelMapper modelMapper, LinksAdder linksAdder, MySecurity mySecurity) {
        super(PaymentMethodController.class, PaymentMethodOutput.class);
        this.modelMapper = modelMapper;
        this.linksAdder = linksAdder;
        this.mySecurity = mySecurity;
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
        if(this.mySecurity.canConsultPaymentMethods()){
            output.add(linksAdder.toPaymentMethods("payment-methods"));

        }
        return output;
    }

    @Override
    public @NonNull CollectionModel<PaymentMethodOutput> toCollectionModel(@NonNull Iterable<? extends PaymentMethod> entities) {
        CollectionModel<PaymentMethodOutput> collectionModel = super.toCollectionModel(entities);
        if(this.mySecurity.canConsultPaymentMethods()){
            collectionModel.add(linksAdder.toPaymentMethods());

        }
        return collectionModel;
    }




}
