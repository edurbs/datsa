package com.github.edurbs.datsa.api.v1.dto.input;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class OrderItemInput {

    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private Long quantity;

    @Nullable
    private String note;
}
