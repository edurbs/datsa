package com.github.edurbs.datsa.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.model.ProductPhoto;
import com.github.edurbs.datsa.domain.repository.ProductRepository;
import com.github.edurbs.datsa.domain.service.PhotoStorageService.NewPhoto;

@Service
public class ProductPhotoCatalogService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PhotoStorageService photoStorageService;

    @Transactional
    public ProductPhoto save(ProductPhoto photo, InputStream photoData){
        String newFileName = photoStorageService.generateFileName(photo.getFileName());
        photo.setFileName(newFileName);
        Long productId = photo.getProduct().getId();
        Optional<ProductPhoto> currentPhoto = productRepository.findPhotoById(productId);
        currentPhoto.ifPresent(productRepository::delete);
        var savesPhotoOnDb = productRepository.save(photo);
        productRepository.flush(); // avoid problems when saving photo data file
        NewPhoto newPhoto = NewPhoto.builder()
            .fileName(newFileName)
            .inputStream(photoData)
            .build();
        photoStorageService.save(newPhoto);
        return savesPhotoOnDb;
    }


}
