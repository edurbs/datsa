package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.PaymentMethodInput;
import com.github.edurbs.datsa.api.v1.dto.output.PaymentMethodOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.Valid;

@SecurityRequirement(name="security_auth")
public interface PaymentMethodControllerOpenApi {
    ResponseEntity<CollectionModel<PaymentMethodOutput>> listAll(ServletWebRequest request);

    ResponseEntity<PaymentMethodOutput> getById(@PathVariable Long id, ServletWebRequest request);

    PaymentMethodOutput add(@RequestBody @Valid PaymentMethodInput input);

    PaymentMethodOutput alter(@PathVariable Long id, @RequestBody @Valid PaymentMethodInput input);

    void delete(@PathVariable Long id);
}
