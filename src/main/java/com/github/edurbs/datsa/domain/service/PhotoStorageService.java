package com.github.edurbs.datsa.domain.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.InputStream;
import java.util.UUID;

public interface PhotoStorageService {

    void save(NewPhoto newPhoto);

    void delete(String fileName);

    FetchedPhoto get(String fileName);

    default String generateFileName(String originalName) {
        return UUID.randomUUID() + "_" + originalName;
    }

    default void replace(String currentPhotoFileName, NewPhoto newPhoto) {
        save(newPhoto);
        if (currentPhotoFileName != null) {
            delete(currentPhotoFileName);
        }
    }

    @Getter
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class NewPhoto {
        String fileName;
        InputStream inputStream;
        String contentType;
    }

    @Getter
    @Builder
    class FetchedPhoto {
        private InputStream inputStream;
        private String url;

        public boolean hasUrl() {
            return url != null;
        }

        public boolean hasInputStream() {
            return inputStream != null;
        }
    }
}
