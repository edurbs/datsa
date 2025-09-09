package com.github.edurbs.datsa.infra.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.edurbs.datsa.domain.filter.DailySalesFilter;
import com.github.edurbs.datsa.domain.service.DailySalesReportService;
import com.github.edurbs.datsa.domain.service.DailySalesService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperReportService implements DailySalesReportService {

    @Autowired
    private DailySalesService dailySalesService;

    @Override
    public byte[] generateDailySalesReport(DailySalesFilter filter, String timeOffset) {

        try {
        var inputStream = this.getClass().getResourceAsStream("/reports/daily-sales.jasper");
        var parameters = new HashMap<String, Object>();
        parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));
        var dailySales = dailySalesService.getDailySales(filter, timeOffset);
        var dataSource = new JRBeanCollectionDataSource(dailySales);
			var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
            e.printStackTrace();
            throw new ReportException("Can't generate the daily sales report.", e);
		}
    }

}
