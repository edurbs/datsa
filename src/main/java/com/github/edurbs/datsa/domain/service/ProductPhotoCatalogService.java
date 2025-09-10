package com.github.edurbs.datsa.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.model.ProductPhoto;
import com.github.edurbs.datsa.domain.repository.ProductRepository;

@Service
public class ProductPhotoCatalogService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ProductPhoto save(ProductPhoto photo){

        Long productId = photo.getProduct().getId();
        Optional<ProductPhoto> currentPhoto = productRepository.findPhotoById(productId);
        currentPhoto.ifPresent(productRepository::delete);

        return productRepository.save(photo);
    }


}
