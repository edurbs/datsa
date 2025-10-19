package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.DailySales;
import com.github.edurbs.datsa.core.springdoc.DailySalesFilterParameter;
import com.github.edurbs.datsa.domain.filter.DailySalesFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Statistics", description = "Statistics report")
public interface StatisticsControllerOpenApi {

    @Operation(hidden = true)
    RepresentationModel<?> statistics();

    @Operation(summary = "Get a daily sales report", responses = {
            @ApiResponse(responseCode = "200",
                    content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DailySales.class))),
                            @Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "binary"))
                    })
    })
    @DailySalesFilterParameter
    List<DailySales> getDailySales(
            @Parameter(hidden = true) DailySalesFilter filter,
            @Parameter(description = "Time Offset", example = "-03:00", required = false) String timeOffset);

    @Operation(hidden = true)
    ResponseEntity<byte[]> getDailySalesPdf(DailySalesFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset);
}
