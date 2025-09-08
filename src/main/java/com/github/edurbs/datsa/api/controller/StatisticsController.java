package com.github.edurbs.datsa.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.dto.DailySales;
import com.github.edurbs.datsa.domain.filter.DailySalesFilter;
import com.github.edurbs.datsa.domain.service.DailySalesService;


@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {

    @Autowired
    DailySalesService dailySalesService;

    @GetMapping("/daily-sales")
    public List<DailySales> getDailySales(DailySalesFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return dailySalesService.getDailySales(filter, timeOffset);
    }

}
