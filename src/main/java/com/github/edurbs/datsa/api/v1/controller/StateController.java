package com.github.edurbs.datsa.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.v1.dto.input.StateInput;
import com.github.edurbs.datsa.api.v1.dto.output.StateOutput;
import com.github.edurbs.datsa.api.v1.mapper.StateMapper;
import com.github.edurbs.datsa.domain.service.StateRegistryService;

@RestController
@RequestMapping("/states")
public class StateController {

    @Autowired
    private StateRegistryService stateRegistryService;

    @Autowired
    private StateMapper stateMapper;

    @GetMapping
    public CollectionModel<StateOutput> listAll() {
        return stateMapper.toCollectionModel(stateRegistryService.getAll());
    }

    @GetMapping("/{stateId}")
    public StateOutput getById(@PathVariable Long stateId) {
        return stateMapper.toModel(stateRegistryService.getById(stateId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StateOutput add(@RequestBody @Valid StateInput stateInput) {
        var state = stateMapper.toDomain(stateInput);
        var stateAdded = stateRegistryService.save(state);
        return stateMapper.toModel(stateAdded);
    }

    @PutMapping("/{stateId}")
    public StateOutput alter(@PathVariable Long stateId, @RequestBody @Valid StateInput stateInput) {
        var state = stateRegistryService.getById(stateId);
        stateMapper.copyToDomain(stateInput, state);
        var alteredState = stateRegistryService.save(state);
        return stateMapper.toModel(alteredState);
    }

    @DeleteMapping("/{stateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long stateId) {
        stateRegistryService.remove(stateId);
    }
}
