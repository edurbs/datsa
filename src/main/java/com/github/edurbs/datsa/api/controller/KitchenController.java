package com.github.edurbs.datsa.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.Kitchen;
import com.github.edurbs.datsa.domain.service.KitchenRegistryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Kitchen add(@RequestBody Kitchen kitchen) {
        return kitchenRegistryService.save(kitchen);
    }

    @PutMapping("/{kitchenId}")
    public ResponseEntity<Kitchen> alter(@PathVariable Long kitchenId, @RequestBody Kitchen kitchen) {
        var alteredKitchen = kitchenRegistryService.getById(kitchenId);
        BeanUtils.copyProperties(kitchen, alteredKitchen, "id");
        alteredKitchen = kitchenRegistryService.save(alteredKitchen);

        return ResponseEntity.ok(alteredKitchen);
    }



}
