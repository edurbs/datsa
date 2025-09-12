package com.github.edurbs.datsa.core.storage;

import com.amazonaws.regions.Regions;
import lombok.AccessLevel;
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
public class StorageProperties {
    private Local local = new Local();
    private S3 s3 = new S3();
    private StorageType storageType = StorageType.S3;

    public enum StorageType {
        LOCAL, S3
    }
    @Getter
    @Setter
    public class Local {
        private Path photoFolder;
    }

    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class S3 {
        String idAccessKey;
        String secretKey;
        String bucket;
        Regions region;
        String photosProductFolder;

    }


}
