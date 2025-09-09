package com.github.edurbs.datsa.infra.service;

import com.github.edurbs.datsa.domain.filter.DailySalesFilter;
import com.github.edurbs.datsa.domain.service.DailySalesReportService;

public class JasperReportService implements DailySalesReportService {

    @Override
    public byte[] generateDailySalesReport(DailySalesFilter filter, String timeOffset) {
        return null;
    }

}
