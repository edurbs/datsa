package com.github.edurbs.datsa.api.v1.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
public class OrderItemInput {

    @Schema(example = "1")
    @NotNull
    private Long productId;

    @Schema(example = "1")
    @NotNull
    @Positive
    private Long quantity;

    @Schema(example = "Some note")
    @Nullable
    private String note;
}
