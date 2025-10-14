package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.dto.input.StateInput;
import com.github.edurbs.datsa.api.v1.dto.output.StateOutput;
import com.github.edurbs.datsa.api.v1.mapper.StateMapper;
import com.github.edurbs.datsa.api.v1.openapi.controller.StateControllerOpenApi;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.domain.service.StateRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/states")
public class StateController implements StateControllerOpenApi {

    @Autowired
    private StateRegistryService stateRegistryService;

    @Autowired
    private StateMapper stateMapper;

    @CheckSecurity.State.CanConsult
    @GetMapping
    @Override
    public CollectionModel<StateOutput> listAll() {
        return stateMapper.toCollectionModel(stateRegistryService.getAll());
    }

    @CheckSecurity.State.CanConsult
    @GetMapping("/{stateId}")
    @Override
    public StateOutput getById(@PathVariable Long stateId) {
        return stateMapper.toModel(stateRegistryService.getById(stateId));
    }

    @CheckSecurity.State.CanEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public StateOutput add(@RequestBody @Valid StateInput stateInput) {
        var state = stateMapper.toDomain(stateInput);
        var stateAdded = stateRegistryService.save(state);
        return stateMapper.toModel(stateAdded);
    }

    @CheckSecurity.State.CanEdit
    @PutMapping("/{stateId}")
    @Override
    public StateOutput alter(@PathVariable Long stateId, @RequestBody @Valid StateInput stateInput) {
        var state = stateRegistryService.getById(stateId);
        stateMapper.copyToDomain(stateInput, state);
        var alteredState = stateRegistryService.save(state);
        return stateMapper.toModel(alteredState);
    }

    @CheckSecurity.State.CanEdit
    @DeleteMapping("/{stateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable Long stateId) {
        stateRegistryService.remove(stateId);
    }
}
