package com.github.edurbs.datsa.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.model.City;
import com.github.edurbs.datsa.domain.repository.CityRepository;

@Service
public class CityRegistryService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRegistryService stateRegistryService;

    public City save(City city) {
        if (city.getState() == null) {
            throw new ModelValidationException("State not informed");
        }
        var state = city.getState();
        if (state.getId() == null) {
            throw new ModelValidationException("State id not informed");
        }
        try {
            stateRegistryService.getById(state.getId());
        } catch (ModelNotFoundException e) {
            throw new ModelValidationException("State id %d not found".formatted(state.getId()));
        }
        return cityRepository.save(city);
    }

    public List<City> getAll() {
        return cityRepository.findAll();
    }

    public City getById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("City id %d does not exists".formatted(id)));
    }

    @Transactional
    public void remove(Long id) {
        if (notExists(id)) {
            throw new ModelNotFoundException("City id %d does not exists".formatted(id));
        }
        if (countStatesByCityId(id) > 0) {
            throw new ModelInUseException("City id %d in use".formatted(id));
        }
        cityRepository.deleteById(id);
        cityRepository.flush();
    }

    private boolean exists(Long id) {
        return cityRepository.existsById(id);
    }

    private boolean notExists(Long id) {
        return !exists(id);
    }

    public Long countStatesByCityId(Long id) {
        return cityRepository.countByStateId(id);

    }
}
