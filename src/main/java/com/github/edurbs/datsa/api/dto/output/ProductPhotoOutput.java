package com.github.edurbs.datsa.api.dto.output;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductPhotoOutput implements OutputModel {

    String fileName;
    String description;
    String contentType;
    Long size;
}
