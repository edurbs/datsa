package com.github.edurbs.datsa.api.v1.dto.input;

import com.github.edurbs.datsa.core.validation.FileContentType;
import com.github.edurbs.datsa.core.validation.FileSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductPhotoInput {

    @NotNull
    @FileSize(max = "10000KB")
    @FileContentType(allowed={MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    MultipartFile file;

    @Schema(example = "Some description")
    @NotBlank
    String description;
}
