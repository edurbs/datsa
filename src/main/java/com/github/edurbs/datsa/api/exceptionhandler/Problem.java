package com.github.edurbs.datsa.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@Schema(name = "Problem")
public class Problem {

    @Schema(example = "400")
    private Integer status;

    @Schema(example = "2025-10-15T20:19:50Z")
    private OffsetDateTime timestamp;

    @Schema(example = "http://datsa.com.br/invalid-data")
    private String type;

    @Schema(example = "Invalid data")
    private String title;

    @Schema(example = "One or more invalid fields. ")
    private String detail;

    @Schema(example = "One or more invalid fields. ")
    private String userMessage;

    @Schema(description = "Error object list")
    private List<Field> fields;

    @Getter
    @Builder
    @Schema(name = "Field")
    public static class Field{

        @Schema(example = "price")
        private String name;

        @Schema(example = "Invalid price")
        private String userMessage;
    }
}
