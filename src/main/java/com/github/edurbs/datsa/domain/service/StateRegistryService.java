package com.github.edurbs.datsa.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.State;
import com.github.edurbs.datsa.domain.repository.StateRepository;

@Service
public class StateRegistryService {
    @Autowired
    private StateRepository stateRepository;

    public State save(State state) {
        return stateRepository.save(state);
    }

    public List<State> getAll() {
        return stateRepository.findAll();
    }

    public State getById(Long id) {
        return stateRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("State id %d does not exist".formatted(id)));
    }

    @Transactional
    public void remove(Long id) {
        if (exists(id)) {
            try {
                stateRepository.deleteById(id);
                stateRepository.flush();
            } catch (DataIntegrityViolationException e) {
                throw new ModelInUseException("State id %d in use".formatted(id));
            }
        } else {
            throw new ModelNotFoundException("State id %d does not exist".formatted(id));
        }
    }

    public boolean exists(Long id) {
        return stateRepository.existsById(id);
    }

}
