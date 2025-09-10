package com.github.edurbs.datsa.domain.repository;

import com.github.edurbs.datsa.domain.model.ProductPhoto;

public interface ProductRepositoryQueries {

    ProductPhoto save(ProductPhoto photo);
    void delete(ProductPhoto photo);
}
