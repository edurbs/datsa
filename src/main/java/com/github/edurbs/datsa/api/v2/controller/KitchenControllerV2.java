package com.github.edurbs.datsa.api.v2.controller;

import com.github.edurbs.datsa.api.v2.dto.input.KitchenInputV2;
import com.github.edurbs.datsa.api.v2.dto.output.KitchenOutputV2;
import com.github.edurbs.datsa.api.v2.mapper.KitchenMapperV2;
import com.github.edurbs.datsa.domain.model.Kitchen;
import com.github.edurbs.datsa.domain.service.KitchenRegistryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/kitchens")
public class KitchenControllerV2 {

    private final KitchenRegistryService kitchenRegistryService;

    private final KitchenMapperV2 kitchenMapperV2;

    private final PagedResourcesAssembler<Kitchen> pagedResourcesAssembler;

    public KitchenControllerV2(KitchenRegistryService kitchenRegistryService, KitchenMapperV2 kitchenMapperV2, PagedResourcesAssembler<Kitchen> pagedResourcesAssembler) {
        this.kitchenRegistryService = kitchenRegistryService;
        this.kitchenMapperV2 = kitchenMapperV2;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping()
    public PagedModel<KitchenOutputV2> listAll(Pageable pageable) {
        Page<Kitchen> kitchensPage = kitchenRegistryService.getAll(pageable);
        return pagedResourcesAssembler.toModel(kitchensPage, kitchenMapperV2);
    }

    @GetMapping("/{kitchenId}")
    public KitchenOutputV2 getById(@PathVariable Long kitchenId) {
        return kitchenMapperV2.toModel(kitchenRegistryService.getById(kitchenId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenOutputV2 add(@RequestBody @Valid KitchenInputV2 kitchenInput) {
        var kitchen = kitchenMapperV2.toDomain(kitchenInput);
        var kitchenAdded = kitchenRegistryService.save(kitchen);
        return kitchenMapperV2.toModel(kitchenAdded);
    }

    @PutMapping("/{kitchenId}")
    public KitchenOutputV2 alter(@PathVariable Long kitchenId, @RequestBody @Valid KitchenInputV2 kitchenInput) {
        var kitchen = kitchenRegistryService.getById(kitchenId);
        kitchenMapperV2.copyToDomain(kitchenInput, kitchen);
        var alteredKitchen = kitchenRegistryService.save(kitchen);
        return kitchenMapperV2.toModel(alteredKitchen);
    }

    @DeleteMapping("/{kitchenId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long kitchenId) {
        kitchenRegistryService.remove(kitchenId);
    }

}
