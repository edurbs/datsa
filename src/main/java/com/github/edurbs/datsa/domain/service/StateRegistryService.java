package com.github.edurbs.datsa.domain.service;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.StateNotFoundException;
import com.github.edurbs.datsa.domain.model.State;
import com.github.edurbs.datsa.domain.repository.StateRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StateRegistryService {

    private final StateRepository stateRepository;

    public StateRegistryService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Transactional
    public State save(State state) {
        return stateRepository.save(state);
    }

    public List<State> getAll() {
        return stateRepository.findAll();
    }

    public State getById(Long id) {
        return stateRepository.findById(id)
                .orElseThrow(() -> new StateNotFoundException(id));
    }

    @Transactional
    public void remove(Long id) {
        if (notExists(id)) {
            throw new StateNotFoundException(id);
        }
        try {
            stateRepository.deleteById(id);
            stateRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ModelInUseException("State id %d in use".formatted(id));
        }
    }

    private boolean exists(Long id) {
        return stateRepository.existsById(id);
    }

    private boolean notExists(Long id) {
        return !exists(id);
    }

}
