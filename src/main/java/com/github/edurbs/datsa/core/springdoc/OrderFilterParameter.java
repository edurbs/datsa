package com.github.edurbs.datsa.core.springdoc;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(
    in = ParameterIn.QUERY,
    name = "userId",
    description = "User ID",
    schema = @Schema(type = "integer", defaultValue = "0")
)
@Parameter(
    in = ParameterIn.QUERY,
    name = "restaurantId",
    description = "Restaurant ID",
    schema = @Schema(type = "integer", defaultValue = "0")
)
@Parameter(
    in = ParameterIn.QUERY,
    name = "beginCreationDate",
    description = "Start date",
    example = "2000-10-31T01:30:00Z"
)
@Parameter(
    in = ParameterIn.QUERY,
    name = "endCreationDate",
    description = "End date",
    example = "2025-10-01T01:30:00Z"
)

public @interface OrderFilterParameter {
}
