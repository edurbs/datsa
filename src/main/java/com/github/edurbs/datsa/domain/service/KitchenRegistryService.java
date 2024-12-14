package com.github.edurbs.datsa.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.Kitchen;
import com.github.edurbs.datsa.domain.repository.KitchenRepository;

@Service
public class KitchenRegistryService {

    @Autowired
    private KitchenRepository kitchenRepository;

    public Kitchen save(Kitchen kitchen) {
        return kitchenRepository.save(kitchen);
    }

    public List<Kitchen> getAll() {
        return kitchenRepository.findAll();
    }

    public Kitchen getById(Long id) {
        return kitchenRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Kitchen id %d does not exists".formatted(id)));
    }

    @Transactional
    public void remove(Long id) {
        if (notExists(id)) {
            throw new ModelNotFoundException("Kitchen id %d does not exists".formatted(id));
        }
        try {
            kitchenRepository.deleteById(id);
            kitchenRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ModelInUseException("Kitchen id %d in use".formatted(id));
        }
    }

    private boolean exists(Long id) {
        return kitchenRepository.existsById(id);
    }

    private boolean notExists(Long id) {
        return !exists(id);
    }

}
