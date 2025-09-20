package com.github.edurbs.datsa.api.controller;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.github.edurbs.datsa.api.dto.input.PaymentMethodInput;
import com.github.edurbs.datsa.api.dto.output.PaymentMethodOutput;
import com.github.edurbs.datsa.api.mapper.PaymentMethodMapper;
import com.github.edurbs.datsa.domain.service.PaymentMethodRegistryService;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    private static final String ETAG_NOT_MODIFIED = "0";

    @Autowired
    private PaymentMethodRegistryService registryService;

    @Autowired
    private PaymentMethodMapper mapper;

    @GetMapping
    public ResponseEntity<CollectionModel<PaymentMethodOutput>> listAll(ServletWebRequest request) {
        String eTag = getETag(request);
        if(ETAG_NOT_MODIFIED.equals(eTag)){
            return null;
        }
        CollectionModel<PaymentMethodOutput> list = mapper.toCollectionModel(registryService.getAll());
        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
            .eTag(eTag) // add the tag
            .body(list);
    }

    private String getETag(ServletWebRequest request){
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        OffsetDateTime lastUpdate = registryService.getMaxUpdateAt();
        String eTag = ETAG_NOT_MODIFIED; // if no date (null), then 0
        if(lastUpdate!=null){
            eTag = String.valueOf(lastUpdate.toEpochSecond());
        }
        if(request.checkNotModified(eTag)){ // if no modification
            eTag = ETAG_NOT_MODIFIED; // use the local cache
        }
        return eTag;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodOutput> getById(@PathVariable Long id, ServletWebRequest request) {
        String eTag = getETag(request);
        if(ETAG_NOT_MODIFIED.equals(eTag)){
            return null;
        }

        PaymentMethodOutput paymentMethodOutput = mapper.toModel(registryService.getById(id));
        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
            .body(paymentMethodOutput);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethodOutput add(@RequestBody @Valid PaymentMethodInput input) {
        var domain = mapper.toDomain(input);
        var domainAdded = registryService.save(domain);
        return mapper.toModel(domainAdded);
    }

    @PutMapping("/{id}")
    public PaymentMethodOutput alter(@PathVariable Long id, @RequestBody @Valid PaymentMethodInput input) {
        var domain = registryService.getById(id);
        mapper.copyToDomain(input, domain);
        var alteredDomain = registryService.save(domain);
        return mapper.toModel(alteredDomain);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        registryService.remove(id);
    }

}
