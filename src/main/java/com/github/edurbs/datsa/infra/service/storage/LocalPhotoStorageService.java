package com.github.edurbs.datsa.infra.service.storage;

import com.github.edurbs.datsa.core.storage.StorageProperties;
import com.github.edurbs.datsa.domain.service.PhotoStorageService;
import lombok.AllArgsConstructor;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@AllArgsConstructor
public class LocalPhotoStorageService implements PhotoStorageService {

    private StorageProperties properties;

    @Override
    public void save(NewPhoto newPhoto) {
        Path filePath = getFilePath(newPhoto.getFileName());
        try (InputStream in = newPhoto.getInputStream(); OutputStream out = Files.newOutputStream(filePath)) {
            FileCopyUtils.copy(in, out);
        } catch (IOException e) {
            throw new StorageException("Can't save the file", e);
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            Path filePath = getFilePath(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new StorageException("Can't delete the file.", e);
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
