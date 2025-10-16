package com.github.edurbs.datsa.api.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DailySales {

    @Schema(example = "2025-10-15T13:48:55Z")
    Date date;

    @Schema(example = "1234.00")
    Long totalSales;

    @Schema(example = "12345.00")
    BigDecimal totalBilled;

    // to fix the error in jasperReport with the date field
    public DailySales(java.sql.Date date, Long totalSales, BigDecimal totalBilled) {
        this.date = new Date(date.getTime());
        this.totalSales = totalSales;
        this.totalBilled = totalBilled;
    }
}
