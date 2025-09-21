package com.github.edurbs.datsa.api.v1.dto.output;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductPhotoOutput extends RepresentationModel<ProductPhotoOutput> implements OutputModel {

    String fileName;
    String description;
    String contentType;
    Long size;
}
