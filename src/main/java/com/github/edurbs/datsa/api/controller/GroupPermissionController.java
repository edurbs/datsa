package com.github.edurbs.datsa.api.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.dto.output.PermissionOutput;
import com.github.edurbs.datsa.api.mapper.PermissionMapper;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.exception.PermissionNotFoundException;
import com.github.edurbs.datsa.domain.service.GroupRegistryService;

import lombok.AllArgsConstructor;



@RestController
@RequestMapping("/groups/{groupId}/permissions")
@AllArgsConstructor
public class GroupPermissionController {

    private GroupRegistryService groupRegistryService;

    private PermissionMapper permissionMapper;

    @GetMapping
    public Set<PermissionOutput> getAll(@PathVariable Long groupId) {
        return permissionMapper.toOutputList(groupRegistryService.getAllPermissions(groupId));
    }

    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associatePermission (@PathVariable Long groupId, @PathVariable Long permissionId) {
        try{
            groupRegistryService.associatePermission(groupId, permissionId);
        } catch (PermissionNotFoundException e){
            throw new ModelValidationException(e.getMessage());
        }
    }

    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dissociatePermission (@PathVariable Long groupId, @PathVariable Long permissionId){
        try {
            groupRegistryService.dissociatePermission(groupId, permissionId);
        } catch (PermissionNotFoundException e) {
            throw new ModelValidationException(e.getMessage());
        }
    }


}
