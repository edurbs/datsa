package com.github.edurbs.datsa.api.v1.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.v1.dto.input.OrderInput;
import com.github.edurbs.datsa.api.v1.dto.output.OrderOutput;
import com.github.edurbs.datsa.api.v1.dto.output.OrderSummaryOutput;
import com.github.edurbs.datsa.api.v1.mapper.OrderMapper;
import com.github.edurbs.datsa.api.v1.mapper.OrderSummaryMapper;
import com.github.edurbs.datsa.core.data.PageWrapper;
import com.github.edurbs.datsa.core.data.PageableTranslator;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.filter.OrderFilter;
import com.github.edurbs.datsa.domain.model.Order;
import com.github.edurbs.datsa.domain.model.User;
import com.github.edurbs.datsa.domain.service.OrderRegistryService;




@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Autowired
    OrderRegistryService orderRegistryService;

    @Autowired
    OrderSummaryMapper orderSummaryMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    private PagedResourcesAssembler<Order> pagedResourcesAssembler;

    @Autowired
    private MySecurity mySecurity;

    @CheckSecurity.Orders.CanFindById
    @GetMapping("/{uuid}")
    public OrderOutput getById(@PathVariable String uuid) {
        return orderMapper.toModel(orderRegistryService.getById(uuid));
    }

    @CheckSecurity.Orders.CanSearchWithFilter
    @GetMapping()
    public PagedModel<OrderSummaryOutput> search(OrderFilter orderFilter, Pageable pageable) {
        Pageable translatedPageable = translatePageable(pageable);
        Page<Order> ordersPage = orderRegistryService.getAll(orderFilter, translatedPageable);
        ordersPage = new PageWrapper<>(ordersPage, pageable); // fix link in hateoas when use order
        return pagedResourcesAssembler.toModel(ordersPage, orderSummaryMapper);
    }

    @CheckSecurity.Orders.CanEdit
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public OrderOutput newOrder(@Valid @RequestBody OrderInput orderInput) {
        try {
            Order newOrder = new Order();
            orderMapper.copyToDomain(orderInput, newOrder);
            newOrder.setUser(new User());
            newOrder.getUser().setId(mySecurity.getUserId());
            return orderMapper.toModel(orderRegistryService.newOrder(newOrder));
        } catch (Exception e) {
            throw new ModelValidationException(e.getMessage());
        }
    }

    private Pageable translatePageable(Pageable pageable){
        var map = Map.of(
            "nameUser", "userName");
        return PageableTranslator.translate(pageable, map);
    }


}