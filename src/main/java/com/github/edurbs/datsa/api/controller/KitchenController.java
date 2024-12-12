package com.github.edurbs.datsa.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.domain.model.Kitchen;
import com.github.edurbs.datsa.domain.service.KitchenRegistryService;



@RestController
@RequestMapping("/kitchens")
public class KitchenController {

    @Autowired
    private KitchenRegistryService kitchenRegistryService;

    @GetMapping()
    public List<Kitchen> listAll() {
        return kitchenRegistryService.getAll();
    }

    @GetMapping("/{kitchenId}")
    public Kitchen getById(@PathVariable Long kitchenId) {
        return kitchenRegistryService.getById(kitchenId);
    }


}
