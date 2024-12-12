package com.github.edurbs.datsa.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.domain.model.State;
import com.github.edurbs.datsa.domain.service.StateRegistryService;


@RestController
@RequestMapping("/states")
public class StateController {

    @Autowired
    private StateRegistryService stateRegistryService;

    @GetMapping
    public List<State> listAll() {
        return stateRegistryService.getAll();
    }

}
