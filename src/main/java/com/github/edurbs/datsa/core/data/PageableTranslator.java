package com.github.edurbs.datsa.core.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

public class PageableTranslator {

    private PageableTranslator(){}

    public static Pageable translate (Pageable pageable, Map<String, String> map){
        var orders = pageable.getSort().stream()
            .filter(order -> map.containsKey(order.getProperty())) // only valid orders
            .map(order ->  new Sort.Order(order.getDirection(), map.get(order.getProperty())))
            .toList();
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }
}
