package com.github.edurbs.datsa.infra.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.model.ProductPhoto;
import com.github.edurbs.datsa.domain.repository.ProductRepositoryQueries;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
	@Override
	public ProductPhoto save(ProductPhoto photo) {
		return manager.merge(photo);
	}

    @Transactional
	@Override
	public void delete(ProductPhoto photo) {
		manager.remove(photo);
	}

}
