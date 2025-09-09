package com.github.edurbs.datsa.domain.service;

import com.github.edurbs.datsa.domain.filter.DailySalesFilter;

public interface DailySalesReportService {
    byte[] generateDailySalesReport(DailySalesFilter filter, String timeOffset);

}
