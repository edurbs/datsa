package com.github.edurbs.datsa.api.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

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
    @FileSize(max = "1000KB")
    MultipartFile file;

    @NotBlank
    String description;
}
