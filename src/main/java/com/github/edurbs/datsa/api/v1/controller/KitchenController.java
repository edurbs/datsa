package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.dto.input.KitchenInput;
import com.github.edurbs.datsa.api.v1.dto.output.KitchenOutput;
import com.github.edurbs.datsa.api.v1.mapper.KitchenMapper;
import com.github.edurbs.datsa.domain.model.Kitchen;
import com.github.edurbs.datsa.domain.service.KitchenRegistryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/kitchens")
@Slf4j
public class KitchenController {

    @Autowired
    private KitchenRegistryService kitchenRegistryService;

    @Autowired
    private KitchenMapper kitchenMapper;

    @Autowired
    private PagedResourcesAssembler<Kitchen> pagedResourcesAssembler;

    @GetMapping()
    public PagedModel<KitchenOutput> listAll(Pageable pageable) {
        log.info("Getting kitchens...");
        Page<Kitchen> kitchensPage = kitchenRegistryService.getAll(pageable);
        return pagedResourcesAssembler.toModel(kitchensPage, kitchenMapper);
    }

    @GetMapping("/{kitchenId}")
    public KitchenOutput getById(@PathVariable Long kitchenId) {
        return kitchenMapper.toModel(kitchenRegistryService.getById(kitchenId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenOutput add(@RequestBody @Valid KitchenInput kitchenInput) {
        var kitchen = kitchenMapper.toDomain(kitchenInput);
        var kitchenAdded = kitchenRegistryService.save(kitchen);
        return kitchenMapper.toModel(kitchenAdded);
    }

    @PutMapping("/{kitchenId}")
    public KitchenOutput alter(@PathVariable Long kitchenId, @RequestBody @Valid KitchenInput kitchenInput) {
        var kitchen = kitchenRegistryService.getById(kitchenId);
        kitchenMapper.copyToDomain(kitchenInput, kitchen);
        var alteredKitchen = kitchenRegistryService.save(kitchen);
        return kitchenMapper.toModel(alteredKitchen);
    }

    @DeleteMapping("/{kitchenId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long kitchenId) {
        kitchenRegistryService.remove(kitchenId);
    }

}
