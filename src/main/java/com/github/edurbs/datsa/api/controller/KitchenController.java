package com.github.edurbs.datsa.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
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
    public ResponseEntity<Kitchen> getById(@PathVariable Long kitchenId) {
        try {
            return ResponseEntity.ok(kitchenRegistryService.getById(kitchenId));
        } catch (ModelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
