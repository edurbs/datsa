package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.dto.input.KitchenInput;
import com.github.edurbs.datsa.api.v1.dto.output.KitchenOutput;
import com.github.edurbs.datsa.api.v1.mapper.KitchenMapper;
import com.github.edurbs.datsa.api.v1.openapi.controller.KitchenControllerOpenApi;
import com.github.edurbs.datsa.core.security.CheckSecurity;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/kitchens")
@Slf4j
public class KitchenController implements KitchenControllerOpenApi {

    @Autowired
    private KitchenRegistryService kitchenRegistryService;

    @Autowired
    private KitchenMapper kitchenMapper;

    @Autowired
    private PagedResourcesAssembler<Kitchen> pagedResourcesAssembler;

    @CheckSecurity.Kitchens.CanConsult
    @GetMapping()
    @Override
    public PagedModel<KitchenOutput> listAll(Pageable pageable) {
        log.info("Getting kitchens...");
        Page<Kitchen> kitchensPage = kitchenRegistryService.getAll(pageable);
        return pagedResourcesAssembler.toModel(kitchensPage, kitchenMapper);
    }

    @CheckSecurity.Kitchens.CanConsult
    @GetMapping("/{kitchenId}")
    @Override
    public KitchenOutput getById(@PathVariable Long kitchenId) {
        return kitchenMapper.toModel(kitchenRegistryService.getById(kitchenId));
    }

    @CheckSecurity.Kitchens.CanEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public KitchenOutput add(@RequestBody @Valid KitchenInput kitchenInput) {
        var kitchen = kitchenMapper.toDomain(kitchenInput);
        var kitchenAdded = kitchenRegistryService.save(kitchen);
        return kitchenMapper.toModel(kitchenAdded);
    }

    @CheckSecurity.Kitchens.CanEdit
    @PutMapping("/{kitchenId}")
    @Override
    public KitchenOutput alter(@PathVariable Long kitchenId, @RequestBody @Valid KitchenInput kitchenInput) {
        var kitchen = kitchenRegistryService.getById(kitchenId);
        kitchenMapper.copyToDomain(kitchenInput, kitchen);
        var alteredKitchen = kitchenRegistryService.save(kitchen);
        return kitchenMapper.toModel(alteredKitchen);
    }

    @CheckSecurity.Kitchens.CanEdit
    @DeleteMapping("/{kitchenId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable Long kitchenId) {
        kitchenRegistryService.remove(kitchenId);
    }

}
