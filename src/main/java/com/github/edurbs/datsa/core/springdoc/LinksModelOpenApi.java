package com.github.edurbs.datsa.core.springdoc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinksModelOpenApi {

    private LinkModel rel;

    @Getter
    @Setter
    private static class LinkModel {
        @Schema(example = "http://datsa.com.br/v1/resource/id")
        private String href;
        @Schema(description = "If it's a link that accepts additional request parameters")
        private boolean templated;
    }
}
