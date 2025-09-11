package com.github.edurbs.datsa.domain.service;

import java.io.InputStream;
import java.util.Optional;

import com.github.edurbs.datsa.domain.exception.ProductPhotoNotFoundException;
import com.github.edurbs.datsa.domain.model.Product;
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
        String currentPhotoFileName = null;
        photo.setFileName(newFileName);
        Long productId = photo.getProduct().getId();
        Optional<ProductPhoto> currentPhoto = productRepository.findPhotoById(productId);
        if(currentPhoto.isPresent()){
            currentPhotoFileName = currentPhoto.get().getFileName();
            productRepository.delete(currentPhoto.get());
        };
        var savePhotoOnDb = productRepository.save(photo);
        productRepository.flush(); // avoid problems when saving photo data file
        NewPhoto newPhoto = NewPhoto.builder()
            .fileName(newFileName)
            .inputStream(photoData)
            .build();
        photoStorageService.replace(currentPhotoFileName, newPhoto);
        return savePhotoOnDb;
    }

    public ProductPhoto get(Product product) {
        Long productId = product.getId();
        return productRepository.findPhotoById(productId)
            .orElseThrow(()->new ProductPhotoNotFoundException(productId));
    }

    public InputStream getData(ProductPhoto photo) {
        return photoStorageService.get(photo.getFileName());
    }
}
