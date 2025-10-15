package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.PaymentMethodInput;
import com.github.edurbs.datsa.api.v1.dto.output.PaymentMethodOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@SecurityRequirement(name="security_auth")
@Tag(name="Payment methods", description = "Payment methods registry")
public interface PaymentMethodControllerOpenApi {

    @Operation(description = "List Payment Methods")
    ResponseEntity<CollectionModel<PaymentMethodOutput>> listAll(ServletWebRequest request);

    @Operation(description = "Get a Payment Methods")
    ResponseEntity<PaymentMethodOutput> getById(@Parameter(description = "Payment Methods ID", example = "1", required = true) Long id, ServletWebRequest request);

    @Operation(description = "Add a Payment Methods")
    PaymentMethodOutput add(@RequestBody(description = "New Payment Methods representation") PaymentMethodInput input);

    @Operation(description = "Update a Payment Methods")
    PaymentMethodOutput alter(@Parameter(description = "Payment Methods ID", example = "1", required = true) Long id, @RequestBody(description = "Updated Payment Methods representation") PaymentMethodInput input);

    @Operation(description = "Delete a Payment Methods")
    void delete(@Parameter(description = "Payment Methods ID", example = "1", required = true) Long id);
}
