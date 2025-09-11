package com.github.edurbs.datsa.infra.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.github.edurbs.datsa.domain.service.PhotoStorageService;

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

    private Path getFilePath(String fileName){
         return photoFolder.resolve(Path.of(fileName));
    }

}
