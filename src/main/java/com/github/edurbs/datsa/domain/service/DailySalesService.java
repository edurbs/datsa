package com.github.edurbs.datsa.domain.service;

import java.util.List;

import com.github.edurbs.datsa.api.v1.dto.DailySales;
import com.github.edurbs.datsa.domain.filter.DailySalesFilter;

public interface DailySalesService {

        List<DailySales> getDailySales(DailySalesFilter filter, String timeOffset);
}
