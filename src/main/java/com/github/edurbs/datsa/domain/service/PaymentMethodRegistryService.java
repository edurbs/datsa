package com.github.edurbs.datsa.domain.service;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.PaymentMethod;
import com.github.edurbs.datsa.domain.repository.PaymentMethodRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PaymentMethodRegistryService {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodRegistryService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

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

    public OffsetDateTime getMaxUpdateAt(){
        return paymentMethodRepository.getLastUpdate();
    }

}
