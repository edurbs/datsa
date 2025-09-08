package com.github.edurbs.datsa.domain.service;

import java.util.List;

import com.github.edurbs.datsa.api.dto.DailySales;
import com.github.edurbs.datsa.domain.filter.DailySalesFilter;

public interface DailySalesService {

        List<DailySales> getDailySales(DailySalesFilter filter);
}
