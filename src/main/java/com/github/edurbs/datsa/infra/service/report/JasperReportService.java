package com.github.edurbs.datsa.infra.service.report;

import com.github.edurbs.datsa.api.v1.dto.DailySales;
import com.github.edurbs.datsa.domain.filter.DailySalesFilter;
import com.github.edurbs.datsa.domain.service.DailySalesReportService;
import com.github.edurbs.datsa.domain.service.DailySalesService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Service to generate daily sales report using Jasper Reports.
 * It dependes on Linux of these packages:
 * libxi6
 * libxrender-1
 * libxtst6
 */
@Service
@AllArgsConstructor
public class JasperReportService implements DailySalesReportService {

    private DailySalesService dailySalesService;

    @Override
    public byte[] generateDailySalesReport(DailySalesFilter filter, String timeOffset) {

        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/reports/daily-sales.jasper");
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_LOCALE", Locale.of("pt", "BR"));
            List<DailySales> dailySales = dailySalesService.getDailySales(filter, timeOffset);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dailySales);
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Can't generate the daily sales report.", e);
        }
    }

}
