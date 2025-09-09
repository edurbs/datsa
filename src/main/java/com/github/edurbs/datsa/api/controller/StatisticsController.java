package com.github.edurbs.datsa.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.dto.DailySales;
import com.github.edurbs.datsa.domain.filter.DailySalesFilter;
import com.github.edurbs.datsa.domain.service.DailySalesReportService;
import com.github.edurbs.datsa.domain.service.DailySalesService;


@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {

    @Autowired
    DailySalesService dailySalesService;

    @Autowired
    DailySalesReportService dailySalesReportService;

    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySales> getDailySales(DailySalesFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return dailySalesService.getDailySales(filter, timeOffset);
    }

    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getDailySalesPdf(DailySalesFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        byte[] bytesPdf = dailySalesReportService.generateDailySalesReport(filter, timeOffset);

        var headers =  new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");


        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .headers(headers)
            .body(bytesPdf);
    }

}
