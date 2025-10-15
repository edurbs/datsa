package com.github.edurbs.datsa.api.v1.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductPhotoOutput extends RepresentationModel<ProductPhotoOutput> {

    @Schema(example = "filename.jpg")
    String fileName;

    @Schema(example = "photo of a rice")
    String description;

    @Schema(example = "image/jpg")
    String contentType;

    @Schema(example = "321321546")
    Long size;
}
