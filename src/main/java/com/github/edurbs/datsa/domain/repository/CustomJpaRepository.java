package com.github.edurbs.datsa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomJpaRepository<T, I> extends JpaRepository<T, I> {

    void detach(T entity);
}
