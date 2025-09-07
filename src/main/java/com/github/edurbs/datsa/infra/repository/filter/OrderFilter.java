package com.github.edurbs.datsa.infra.repository.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class OrderFilter {
    Long userId;
    Long restaurantId;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime beginCreationDate;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime endCreationDate;
}
