package com.github.edurbs.datsa.api.v1.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.dto.output.PermissionOutput;
import com.github.edurbs.datsa.api.v1.mapper.PermissionMapper;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.domain.service.PermissionRegistryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/permissions")
@AllArgsConstructor
public class PermissionController {
    private final PermissionRegistryService permissionRegistryService;

    private final PermissionMapper permissionMapper;

    private final LinksAdder linksAdder;

    @CheckSecurity.UsersGroupsPermissions.CanConsult
    @GetMapping
    public CollectionModel<PermissionOutput> findAll(){
        return permissionMapper.toCollectionModel(permissionRegistryService.getAll())
            .add(linksAdder.toPermissions());
    }
}
