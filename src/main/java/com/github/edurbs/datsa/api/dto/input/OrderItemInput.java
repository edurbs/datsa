package com.github.edurbs.datsa.api.dto.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemInput implements InputModel {

    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private Long quantity;

    @Nullable
    private String note;
}
