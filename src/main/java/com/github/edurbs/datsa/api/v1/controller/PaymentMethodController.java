package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.dto.input.PaymentMethodInput;
import com.github.edurbs.datsa.api.v1.dto.output.PaymentMethodOutput;
import com.github.edurbs.datsa.api.v1.mapper.PaymentMethodMapper;
import com.github.edurbs.datsa.api.v1.openapi.controller.PaymentMethodControllerOpenApi;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.domain.service.PaymentMethodRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/v1/payment-methods")
public class PaymentMethodController implements PaymentMethodControllerOpenApi {

    private static final String ETAG_NOT_MODIFIED = "0";

    @Autowired
    private PaymentMethodRegistryService registryService;

    @Autowired
    private PaymentMethodMapper mapper;

    @CheckSecurity.PaymentMethods.CanConsult
    @GetMapping
    @Override
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

    @CheckSecurity.PaymentMethods.CanConsult
    @GetMapping("/{id}")
    @Override
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

    @CheckSecurity.PaymentMethods.CanEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public PaymentMethodOutput add(@RequestBody @Valid PaymentMethodInput input) {
        var domain = mapper.toDomain(input);
        var domainAdded = registryService.save(domain);
        return mapper.toModel(domainAdded);
    }

    @CheckSecurity.PaymentMethods.CanEdit
    @PutMapping("/{id}")
    @Override
    public PaymentMethodOutput alter(@PathVariable Long id, @RequestBody @Valid PaymentMethodInput input) {
        var domain = registryService.getById(id);
        mapper.copyToDomain(input, domain);
        var alteredDomain = registryService.save(domain);
        return mapper.toModel(alteredDomain);
    }

    @CheckSecurity.PaymentMethods.CanEdit
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable Long id){
        registryService.remove(id);
    }

    private String getETag(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        OffsetDateTime lastUpdate = registryService.getMaxUpdateAt();
        String eTag = PaymentMethodController.ETAG_NOT_MODIFIED; // if no date (null), then 0
        if (lastUpdate != null) {
            eTag = String.valueOf(lastUpdate.toEpochSecond());
        }
        if (request.checkNotModified(eTag)) { // if no modification
            eTag = PaymentMethodController.ETAG_NOT_MODIFIED; // use the local cache
        }
        return eTag;
    }

}
