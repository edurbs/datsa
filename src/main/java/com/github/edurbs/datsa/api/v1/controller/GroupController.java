package com.github.edurbs.datsa.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.github.edurbs.datsa.api.v1.dto.input.GroupInput;
import com.github.edurbs.datsa.api.v1.dto.output.GroupOutput;
import com.github.edurbs.datsa.api.v1.mapper.GroupMapper;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.domain.exception.GroupNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.service.GroupRegistryService;

@RestController
@RequestMapping("/v1/groups")
public class GroupController {

    @Autowired
    private GroupRegistryService registryService;

    @Autowired
    private GroupMapper mapper;

    @CheckSecurity.UsersGroupsPermissions.CanConsult
    @GetMapping
    public CollectionModel<GroupOutput> getAll() {
        var groups = registryService.getAll();
        return mapper.toCollectionModel(groups);
    }

    @CheckSecurity.UsersGroupsPermissions.CanConsult
    @GetMapping("/{id}")
    public GroupOutput getOne(@PathVariable Long id) {
        try {
            var group = registryService.getById(id);
            return mapper.toModel(group);
        } catch (GroupNotFoundException e) {
            throw new ModelNotFoundException(e.getMessage());
        }
    }

    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupOutput add(@RequestBody GroupInput groupInput) {
        var group = mapper.toDomain(groupInput);
        var groupAdded = registryService.save(group);
        return mapper.toModel(groupAdded);
    }

    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @PutMapping("/{id}")
    public GroupOutput alter(@PathVariable Long id, @RequestBody GroupInput input) {
        var group = registryService.getById(id);
        mapper.copyToDomain(input, group);
        var groupAltered = registryService.save(group);
        return mapper.toModel(groupAltered);
    }

    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        registryService.remove(id);
    }

}
