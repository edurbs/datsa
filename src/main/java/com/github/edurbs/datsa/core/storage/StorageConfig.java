package com.github.edurbs.datsa.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.github.edurbs.datsa.domain.service.PhotoStorageService;
import com.github.edurbs.datsa.infra.service.storage.LocalPhotoStorageService;
import com.github.edurbs.datsa.infra.service.storage.S3PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    @ConditionalOnProperty(name = "datsa.storage.storage-type", havingValue = "s3")
    public AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(
                storageProperties.getS3().getIdAccessKey(),
                storageProperties.getS3().getSecretKey());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(storageProperties.getS3().getRegion())
                .build();
    }

    @Bean
    public PhotoStorageService photoStorageService(S3PhotoStorageService s3, LocalPhotoStorageService local) {
        if (StorageProperties.StorageType.S3.equals(storageProperties.getStorageType())) {
            return s3;
        } else {
            return local;
        }
    }

}
