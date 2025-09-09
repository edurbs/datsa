package com.github.edurbs.datsa.api.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.github.edurbs.datsa.core.validation.FileContentType;
import com.github.edurbs.datsa.core.validation.FileSize;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductPhotoInput {

    @NotNull
    @FileSize(max = "10000KB")
    @FileContentType(allowed={MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    MultipartFile file;

    @NotBlank
    String description;
}
