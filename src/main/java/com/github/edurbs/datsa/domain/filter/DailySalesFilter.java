package com.github.edurbs.datsa.domain.filter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.OffsetDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class DailySalesFilter {
    Long restaurantId;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime creationDateFrom;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime creationDateTo;
}
