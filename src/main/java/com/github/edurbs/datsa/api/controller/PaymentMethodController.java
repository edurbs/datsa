package com.github.edurbs.datsa.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.dto.input.PaymentMethodInput;
import com.github.edurbs.datsa.api.dto.output.PaymentMethodOutput;
import com.github.edurbs.datsa.api.mapper.PaymentMethodMapper;
import com.github.edurbs.datsa.domain.service.PaymentMethodRegistryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/paymentmethods")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodRegistryService registryService;

    @Autowired
    private PaymentMethodMapper mapper;

    @GetMapping
    public List<PaymentMethodOutput> listAll() {
        return mapper.toOutputList(registryService.getAll());
    }

    @GetMapping("/{id}")
    public PaymentMethodOutput getById(@PathVariable Long id) {
        return mapper.toOutput(registryService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethodOutput add(@RequestBody @Valid PaymentMethodInput input) {
        var domain = mapper.toDomain(input);
        var domainAdded = registryService.save(domain);
        return mapper.toOutput(domainAdded);
    }

    @PutMapping("/{id}")
    public PaymentMethodOutput alter(@PathVariable Long id, @RequestBody @Valid PaymentMethodInput input) {
        var domain = registryService.getById(id);
        mapper.copyToDomain(input, domain);
        var alteredDomain = registryService.save(domain);
        return mapper.toOutput(alteredDomain);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        registryService.remove(id);
    }

}
