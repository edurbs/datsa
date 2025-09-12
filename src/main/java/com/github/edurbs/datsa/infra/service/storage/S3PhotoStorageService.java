package com.github.edurbs.datsa.infra.service.storage;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.github.edurbs.datsa.core.storage.StorageProperties;
import com.github.edurbs.datsa.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3PhotoStorageService implements PhotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void save(NewPhoto newPhoto) {
        try {
            String filePath = getFilePath(newPhoto.getFileName());
            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(newPhoto.getContentType());
            var putObjectRequest = new PutObjectRequest(
                storageProperties.getS3().getBucket(),
                filePath,
                newPhoto.getInputStream(),
                objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Can't send the file to Amazon S3", e);
        }

    }

    private String getFilePath(String fileName) {
        return "%s/%s".formatted(storageProperties.getS3().getPhotosProductFolder(), fileName);
    }

    @Override
    public void delete(String fileName) {

    }

    @Override
    public InputStream get(String fileName) {
        return null;
    }
}
