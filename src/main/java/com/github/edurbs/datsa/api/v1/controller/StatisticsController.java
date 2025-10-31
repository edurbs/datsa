package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.dto.DailySales;
import com.github.edurbs.datsa.api.v1.openapi.controller.StatisticsControllerOpenApi;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.domain.filter.DailySalesFilter;
import com.github.edurbs.datsa.domain.service.DailySalesReportService;
import com.github.edurbs.datsa.domain.service.DailySalesService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/statistics")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticsController implements StatisticsControllerOpenApi {

    DailySalesService dailySalesService;
    DailySalesReportService dailySalesReportService;
    LinksAdder linksAdder;

    public StatisticsController(DailySalesService dailySalesService, DailySalesReportService dailySalesReportService, LinksAdder linksAdder) {
        this.dailySalesService = dailySalesService;
        this.dailySalesReportService = dailySalesReportService;
        this.linksAdder = linksAdder;
    }

    @CheckSecurity.Statistics.CanConsult
    @GetMapping
    @Override
    public RepresentationModel<?> statistics(){
        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(linksAdder.toDailySales());
        return model;
    }

    @CheckSecurity.Statistics.CanConsult
    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public List<DailySales> getDailySales(DailySalesFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return dailySalesService.getDailySales(filter, timeOffset);
    }

    @CheckSecurity.Statistics.CanConsult
    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    @Override
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
