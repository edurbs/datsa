package com.github.edurbs.datsa.api.v1.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class KitchenIdInput  {

    @NotNull
    private Long id;
}
