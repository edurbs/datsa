package com.github.edurbs.datsa.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.PaymentMethod;
import com.github.edurbs.datsa.infra.repository.PaymentMethodRepository;

@Service
public class PaymentMethodRegistryService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Transactional
    public PaymentMethod save(PaymentMethod paymentMethod){
        return paymentMethodRepository.save(paymentMethod);
    }

    public List<PaymentMethod> getAll(){
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod getById(Long id){
        return paymentMethodRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("PaymentMethod id %d does not exists".formatted(id)));
    }

    @Transactional
    public void remove(Long id){
        if(notExists(id)){
            throw new ModelNotFoundException("PaymentMethod id %d does not exists".formatted(id));
        }
        try {
            paymentMethodRepository.deleteById(id);
            paymentMethodRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ModelInUseException("PaymentMethod id %d in use".formatted(id));
        }
    }

    private boolean notExists(Long id){
        return !paymentMethodRepository.existsById(id);
    }

}
