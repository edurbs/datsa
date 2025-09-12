package com.github.edurbs.datsa.infra.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.github.edurbs.datsa.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3PhotoStorageService implements PhotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public void save(NewPhoto newPhoto) {

    }

    @Override
    public void delete(String fileName) {

    }

    @Override
    public InputStream get(String fileName) {
        return null;
    }
}
