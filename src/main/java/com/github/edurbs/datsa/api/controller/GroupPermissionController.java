package com.github.edurbs.datsa.api.controller;

import com.github.edurbs.datsa.api.LinksAdder;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private LinksAdder linksAdder;

    @GetMapping
    public CollectionModel<PermissionOutput> getAll(@PathVariable Long groupId) {
        CollectionModel<PermissionOutput> permissionOutputs = permissionMapper.toCollectionModel(groupRegistryService.getAllPermissions(groupId))
            .add(linksAdder.toPermissions(groupId, "permissions"))
            .add(linksAdder.toAssociatePermissions(groupId, "associate"));
        permissionOutputs.getContent().forEach(permissionOutput -> {
            permissionOutput.add(linksAdder.toDissociatePermission(groupId, permissionOutput.getId(), "dissociate"));
        });
        return  permissionOutputs;
    }

    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associatePermission (@PathVariable Long groupId, @PathVariable Long permissionId) {
        try{
            groupRegistryService.associatePermission(groupId, permissionId);
        } catch (PermissionNotFoundException e){
            throw new ModelValidationException(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> dissociatePermission (@PathVariable Long groupId, @PathVariable Long permissionId){
        try {
            groupRegistryService.dissociatePermission(groupId, permissionId);
        } catch (PermissionNotFoundException e) {
            throw new ModelValidationException(e.getMessage());
        }
        return ResponseEntity.notFound().build();
    }


}
