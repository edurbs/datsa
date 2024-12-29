package com.github.edurbs.datsa.domain.service;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.exception.CityNotFoundException;
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

    @Transactional
    public City save(City city) {
        if (city.getState() == null) {
            throw new ModelValidationException("State not informed");
        }
        var state = city.getState();
        if (state.getId() == null) {
            throw new ModelValidationException("State id not informed");
        }
        stateRegistryService.getById(state.getId());
        return cityRepository.save(city);
    }

    public List<City> getAll() {
        return cityRepository.findAll();
    }

    public City getById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(launtCityNotFoundException(id));
    }

    @Transactional
    public void remove(Long id) {
        if (notExists(id)) {
            launtCityNotFoundException(id);
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

    private Supplier<CityNotFoundException> launtCityNotFoundException(Long id) {
        return () -> new CityNotFoundException("City id %d does not exists".formatted(id));
    }

    public Long countStatesByCityId(Long id) {
        return cityRepository.countByStateId(id);

    }
}
