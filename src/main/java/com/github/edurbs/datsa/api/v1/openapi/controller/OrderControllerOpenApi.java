package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.OrderInput;
import com.github.edurbs.datsa.api.v1.dto.output.OrderOutput;
import com.github.edurbs.datsa.api.v1.dto.output.OrderSummaryOutput;
import com.github.edurbs.datsa.core.springdoc.OrderFilterParameter;
import com.github.edurbs.datsa.core.springdoc.PageableParameter;
import com.github.edurbs.datsa.domain.filter.OrderFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@SecurityRequirement(name="security_auth")
@Tag(name = "Orders", description = "Orders registry")
public interface OrderControllerOpenApi {

    @Operation(summary = "Get an order" )
    OrderOutput getById(@Parameter(description = "Order UUID", example = "eb1b17c8-5f0d-464e-8384-bd38f60b9784", required = true) String uuid);

    @Operation(summary = "Get orders")
    @PageableParameter
    @OrderFilterParameter
    PagedModel<OrderSummaryOutput> search(@Parameter(hidden = true) OrderFilter orderFilter, @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "New order")
    OrderOutput newOrder(@Parameter(description = "New order representation", required = true) OrderInput orderInput);

}
