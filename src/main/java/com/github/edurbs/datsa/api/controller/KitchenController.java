package com.github.edurbs.datsa.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.github.edurbs.datsa.api.dto.input.KitchenInput;
import com.github.edurbs.datsa.api.dto.output.KitchenOutput;
import com.github.edurbs.datsa.api.mapper.KitchenMapper;
import com.github.edurbs.datsa.domain.model.Kitchen;
import com.github.edurbs.datsa.domain.service.KitchenRegistryService;

@RestController
@RequestMapping("/kitchens")
public class KitchenController {

    @Autowired
    private KitchenRegistryService kitchenRegistryService;

    @Autowired
    private KitchenMapper kitchenMapper;

    @GetMapping()
    public List<KitchenOutput> listAll() {
        return kitchenMapper.toOutputList(kitchenRegistryService.getAll());
    }

    @GetMapping("/{kitchenId}")
    public KitchenOutput getById(@PathVariable Long kitchenId) {
        return kitchenMapper.toOutput(kitchenRegistryService.getById(kitchenId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenOutput add(@RequestBody @Valid KitchenInput kitchenInput) {
        var kitchen = kitchenMapper.toDomain(kitchenInput);
        var kitchenAdded = kitchenRegistryService.save(kitchen);
        return kitchenMapper.toOutput(kitchenAdded);
    }

    @PutMapping("/{kitchenId}")
    public KitchenOutput alter(@PathVariable Long kitchenId, @RequestBody @Valid KitchenInput kitchenInput) {
        var kitchen = kitchenRegistryService.getById(kitchenId);
        kitchenMapper.copyToDomain(kitchenInput, kitchen);
        var alteredKitchen = kitchenRegistryService.save(kitchen);
        return kitchenMapper.toOutput(alteredKitchen);
    }

    @DeleteMapping("/{kitchenId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long kitchenId) {
            kitchenRegistryService.remove(kitchenId);
    }

}
