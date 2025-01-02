package com.github.edurbs.datsa.api.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.dto.input.PaymentMethodInput;
import com.github.edurbs.datsa.api.dto.output.PaymentMethodOutput;
import com.github.edurbs.datsa.domain.model.PaymentMethod;

@Component
public class PaymentMethodMapper {

    @Autowired
    ModelMapper modelMapper;

    public PaymentMethod toDomain(PaymentMethodInput input){
        return modelMapper.map(input, PaymentMethod.class);
    }

    public void copyToDomain(PaymentMethodInput input, PaymentMethod domain){
        modelMapper.map(input, domain);
    }

    public PaymentMethodOutput toOutput(PaymentMethod domain){
        return modelMapper.map(domain, PaymentMethodOutput.class);
    }

    public List<PaymentMethodOutput> toOutputList(List<PaymentMethod> domains){
        return domains.stream()
                .map(this::toOutput)
                .toList();
    }

    public Set<PaymentMethodOutput> toOutputSet(Set<PaymentMethod> domains){
        return domains.stream()
                .map(this::toOutput)
                .collect(Collectors.toSet());
    }

}
