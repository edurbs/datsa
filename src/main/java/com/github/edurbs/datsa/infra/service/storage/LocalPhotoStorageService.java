package com.github.edurbs.datsa.infra.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.github.edurbs.datsa.core.storage.StorageProperties;
import com.github.edurbs.datsa.domain.service.PhotoStorageService;

public class LocalPhotoStorageService implements PhotoStorageService {

    @Autowired
    private StorageProperties properties;

    @Override
    public void save(NewPhoto newPhoto) {
        try {
            Path filePath = getFilePath(newPhoto.getFileName());
            FileCopyUtils.copy(newPhoto.getInputStream(), Files.newOutputStream(filePath));
        } catch (IOException e) {
            throw new StorageException("Can't storage the file", e);
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            Path filePath = getFilePath(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new StorageException("Cant delete the file.", e);
        }
    }

    @Override
    public FetchedPhoto get(String fileName) {
        try {
            Path fileNamePath = getFilePath(fileName);
            return FetchedPhoto.builder()
                .inputStream(Files.newInputStream(fileNamePath))
                .build();
        } catch (IOException e) {
            throw new StorageException("Can't read the file", e);
        }
    }

    private Path getFilePath(String fileName) {
        return properties.getLocal().getPhotoFolder()
            .resolve(Path.of(fileName));

    }

}
