package com.github.edurbs.datsa.api.dto.output;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserOutput implements OutputModel {

    private Long id;
    private String name;
    private String email;
}
