package com.github.edurbs.datsa.api.controller;

import com.github.edurbs.datsa.api.LinksAdder;
import com.github.edurbs.datsa.api.dto.output.PermissionOutput;
import com.github.edurbs.datsa.api.mapper.PermissionMapper;
import com.github.edurbs.datsa.domain.service.PermissionRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissions")
@AllArgsConstructor
public class PermissionController {
    private final PermissionRegistryService permissionRegistryService;

    private final PermissionMapper permissionMapper;

    private final LinksAdder linksAdder;

    @GetMapping
    public CollectionModel<PermissionOutput> findAll(){
        return permissionMapper.toCollectionModel(permissionRegistryService.getAll())
            .add(linksAdder.toPermissions());
    }
}
