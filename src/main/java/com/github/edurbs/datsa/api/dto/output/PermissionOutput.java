package com.github.edurbs.datsa.api.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionOutput implements OutputModel {

    private Long id;
    private String name;
    private String description;
}
