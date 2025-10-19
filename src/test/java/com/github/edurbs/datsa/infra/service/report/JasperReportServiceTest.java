package com.github.edurbs.datsa.infra.service.report;

import com.github.edurbs.datsa.api.v1.dto.DailySales;
import com.github.edurbs.datsa.domain.filter.DailySalesFilter;
import com.github.edurbs.datsa.domain.service.DailySalesService;
import jakarta.persistence.PersistenceException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JasperReportServiceTest {

    @Mock
    DailySalesService dailySalesService;

    @InjectMocks
    JasperReportService jasperReportService;

    @Test
    void givenFilterAndOffset_whenGenerate_thenReturnByte() {
        // Arrange
        DailySalesFilter filter = new DailySalesFilter();
        String timeOffset = "+00:00";
        List<DailySales> dailySalesList = List.of(new DailySales(Date.valueOf(OffsetDateTime.now().toLocalDate()), 10L, new BigDecimal(100L)));
        when(dailySalesService.getDailySales(filter, timeOffset)).thenReturn(dailySalesList);

        // Act
        byte[] result = jasperReportService.generateDailySalesReport(filter, timeOffset);

        // Assert
        assertNotNull(result);
        assertTrue(result.length > 0);
        verify(dailySalesService).getDailySales(filter, timeOffset);

    }

    @Test
    void givenFilterAndOffset_whenDailySalesServiceThrowsException_thenThrowReportException() {
        // Arrange
        DailySalesFilter filter = new DailySalesFilter();
        String timeOffset = "+00:00";

        when(dailySalesService.getDailySales(filter, timeOffset))
                .thenThrow(new PersistenceException("Database error"));

        // Act & Assert
        ReportException exception = assertThrows(ReportException.class, () ->
                jasperReportService.generateDailySalesReport(filter, timeOffset));
        assertEquals("Can't generate the daily sales report.", exception.getMessage());
        assertNotNull(exception.getCause());
    }

    @Test
    void givenFilterAndOffset_whenJasperThrowsException_thenThrowReportException() {
        // Arrange
        DailySalesFilter filter = new DailySalesFilter();
        String timeOffset = "+00:00";

        try (MockedStatic<JasperFillManager> mockedStatic = mockStatic(JasperFillManager.class)) {
            mockedStatic.when(() -> JasperFillManager.fillReport(
                            any(InputStream.class),
                            anyMap(),
                            any(JRBeanCollectionDataSource.class)))
                    .thenThrow(new JRException("Jasper error"));

            // Act & Assert
            ReportException exception = assertThrows(ReportException.class, () ->
                    jasperReportService.generateDailySalesReport(filter, timeOffset));
            assertEquals("Can't generate the daily sales report.", exception.getMessage());
            assertNotNull(exception.getCause());
        }

    }

}
