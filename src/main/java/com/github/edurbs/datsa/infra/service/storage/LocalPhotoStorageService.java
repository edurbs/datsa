package com.github.edurbs.datsa.infra.service.storage;

import com.github.edurbs.datsa.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalPhotoStorageService implements PhotoStorageService {

    @Value("${datsa.storage.local.photo-folder}")
    private Path photoFolder;

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
    public InputStream get(String fileName) {
            Path fileNamePath = getFilePath(fileName);
        try (InputStream inputStream = Files.newInputStream(fileNamePath)) {
            return inputStream;
        } catch (IOException e) {
            throw new StorageException("Can't read the file", e);
        }
    }

    private Path getFilePath(String fileName) {
        return photoFolder.resolve(Path.of(fileName));

    }

}
