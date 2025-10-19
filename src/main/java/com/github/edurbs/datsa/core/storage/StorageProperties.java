package com.github.edurbs.datsa.core.storage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties("datsa.storage")
@AllArgsConstructor
public class StorageProperties {

    private Local local;
    private S3 s3;
    private StorageType storageType;

    public enum StorageType {
        LOCAL, S3
    }

    @Getter
    @Setter
    public static class Local {
        private Path photoFolder;
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class S3 {
        String idAccessKey;
        String secretKey;
        String bucket;
        String region;
        String photosProductFolder;
    }
}
