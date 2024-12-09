package com.github.edurbs.datsa.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.model.Kitchen;

@Component
public class KitchenRegistry {

    @PersistenceContext
    private EntityManager manager;

    public List<Kitchen> findAll() {
        return manager.createQuery("from Kitchen", Kitchen.class).getResultList();
    }

    @Transactional
    public Kitchen save(Kitchen kitchen){
        return manager.merge(kitchen);
    }

    public Kitchen findById(Long id) {
        return manager.find(Kitchen.class, id);
    }

    @Transactional
    public void delete(Long id) {
        manager.remove(findById(id));
    }
}
