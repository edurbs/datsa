package com.github.edurbs.datsa.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

public interface PhotoStorageService {
    void save(NewPhoto  newPhoto);

    default String generateFileName(String originalName){
        return UUID.randomUUID() + "_" + originalName;
    }

    @Getter
    @Builder
    @FieldDefaults(level=AccessLevel.PRIVATE, makeFinal=true)
    class NewPhoto {
        String fileName;
        InputStream inputStream;

    }
}
