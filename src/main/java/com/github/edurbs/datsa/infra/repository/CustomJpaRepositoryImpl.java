package com.github.edurbs.datsa.infra.repository;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.github.edurbs.datsa.domain.repository.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T, I> extends SimpleJpaRepository<T, I> implements CustomJpaRepository<T, I> {

    private EntityManager entityManager;

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager){
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public void detach(T entity){
        entityManager.detach(entity);
    }
}
