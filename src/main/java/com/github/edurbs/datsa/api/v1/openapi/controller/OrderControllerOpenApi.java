package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.OrderInput;
import com.github.edurbs.datsa.api.v1.dto.output.OrderOutput;
import com.github.edurbs.datsa.api.v1.dto.output.OrderSummaryOutput;
import com.github.edurbs.datsa.domain.filter.OrderFilter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@SecurityRequirement(name="security_auth")
public interface OrderControllerOpenApi {
    OrderOutput getById(@PathVariable String uuid);

    PagedModel<OrderSummaryOutput> search(OrderFilter orderFilter, Pageable pageable);

    OrderOutput newOrder(@Valid @RequestBody OrderInput orderInput);

}
