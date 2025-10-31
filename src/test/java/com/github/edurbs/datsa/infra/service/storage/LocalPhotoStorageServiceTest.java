package com.github.edurbs.datsa.infra.service.storage;

import com.github.edurbs.datsa.core.storage.StorageProperties;
import com.github.edurbs.datsa.domain.service.PhotoStorageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocalPhotoStorageServiceTest {

    Path photoPath;
    PhotoStorageService.NewPhoto newPhoto;

    @Mock
    StorageProperties storageProperties;

    @Mock
    StorageProperties.Local local;

    @InjectMocks
    LocalPhotoStorageService sut;

    @BeforeEach
    void prepare() throws IOException {
        photoPath = Paths.get("src", "test", "resources");
        if (!Files.exists(photoPath)) {
            Files.createDirectories(photoPath);
        }
        InputStream inputStream = new ByteArrayInputStream("Test file content".getBytes());
        newPhoto = PhotoStorageService.NewPhoto.builder()
                .fileName("testFile.txt")
                .inputStream(inputStream)
                .contentType("application/text")
                .build();
        when(local.getPhotoFolder()).thenReturn(photoPath);
        when(storageProperties.getLocal()).thenReturn(local);
    }

    @AfterEach
    void finish() throws IOException {
        Files.deleteIfExists(getPhotoPath());
    }

    private Path getPhotoPath() {
        return photoPath.resolve(Path.of(newPhoto.getFileName()));
    }

    @Test
    void givenNewPhoto_whenSave_thenFileIsCreated()  {
        // Arrange

        // Act & Assert
        assertDoesNotThrow(() -> sut.save(newPhoto));
        assertTrue(Files.exists(getPhotoPath()));
    }

    @Test
    void givenNewPhoto_whenSaveThrowsException_thenThrowsStorageException() {
        // Arrange
        try (MockedStatic<FileCopyUtils> mockedStatic = mockStatic(FileCopyUtils.class)) {
            mockedStatic.when(() -> FileCopyUtils.copy(
                    any(InputStream.class), any(OutputStream.class)
            )).thenThrow(new IOException());

            // Act & Assert
            assertThrows(StorageException.class, () -> sut.save(newPhoto));
        }
    }

    @Test
    void givenStringFileName_whenDelete_thenFileIsDeleted() {
        // Arrange
        String filename = newPhoto.getFileName();

        // Act & Assert
        assertDoesNotThrow(() -> sut.delete(filename));
    }

    @Test
    void givenNewPhoto_whenDeleteThrowsException_thenThrowsStorageException() {
        // Arrange
        try (MockedStatic<Files> mockedStatic = mockStatic(Files.class)) {
            mockedStatic.when(() -> Files.deleteIfExists(any(Path.class)))
                    .thenThrow(new IOException());

            // Act & Assert
            String fileName = newPhoto.getFileName();
            assertThrows(StorageException.class, () -> sut.delete(fileName));
        }
    }

    @Test
    void givenStringFileName_whenGet_thenReturnsFetchedPhoto() throws IOException {
        // Arrange
        Files.newOutputStream(getPhotoPath()).close();  // Ensure file is created

        // Act & Assert
        assertDoesNotThrow(() -> {
            PhotoStorageService.FetchedPhoto fetchedPhoto = sut.get(newPhoto.getFileName());
            assertNotNull(fetchedPhoto);
        });
    }

    @SuppressWarnings("resource")
    @Test
    void givenNewPhoto_whenGetThrowsException_thenThrowsStorageException() {
        // Arrange
        String filename = newPhoto.getFileName();
        try (MockedStatic<Files> mockedStatic = mockStatic(Files.class)) {
            mockedStatic.when(() -> Files.newInputStream(any(Path.class)))
                    .thenThrow(new IOException());

            // Act & Assert
            assertThrows(StorageException.class, () -> sut.get(filename));
        }
    }

}