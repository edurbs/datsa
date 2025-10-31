package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.dto.output.PermissionOutput;
import com.github.edurbs.datsa.api.v1.mapper.PermissionMapper;
import com.github.edurbs.datsa.api.v1.openapi.controller.GroupPermissionControllerOpenApi;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.exception.PermissionNotFoundException;
import com.github.edurbs.datsa.domain.model.Permission;
import com.github.edurbs.datsa.domain.service.GroupRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;



@RestController
@RequestMapping("/v1/groups/{groupId}/permissions")
@AllArgsConstructor
public class GroupPermissionController implements GroupPermissionControllerOpenApi {

    private GroupRegistryService groupRegistryService;

    private PermissionMapper permissionMapper;

    private LinksAdder linksAdder;

    private MySecurity mySecurity;

    @CheckSecurity.UsersGroupsPermissions.CanConsult
    @GetMapping
    @Override
    public CollectionModel<PermissionOutput> getAll(@PathVariable Long groupId) {
        Collection<Permission> allPermissions = groupRegistryService.getAllPermissions(groupId);
        CollectionModel<PermissionOutput> permissionOutputs = permissionMapper.toCollectionModel(allPermissions);
        permissionOutputs.removeLinks();
        permissionOutputs.add(linksAdder.toPermissions(groupId, "permissions"));
        if(this.mySecurity.canEditUsersGroupsPermissions()){
            permissionOutputs.add(linksAdder.toAssociatePermissions(groupId, "associate"));
            permissionOutputs.getContent().forEach(permissionOutput ->
                permissionOutput.add(linksAdder.toDissociatePermission(groupId, permissionOutput.getId(), "dissociate"))
            );
        }
        return  permissionOutputs;
    }

    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> associatePermission(@PathVariable Long groupId, @PathVariable Long permissionId) {
        try{
            groupRegistryService.associatePermission(groupId, permissionId);
        } catch (PermissionNotFoundException e){
            throw new ModelValidationException(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> dissociatePermission(@PathVariable Long groupId, @PathVariable Long permissionId){
        try {
            groupRegistryService.dissociatePermission(groupId, permissionId);
        } catch (PermissionNotFoundException e) {
            throw new ModelValidationException(e.getMessage());
        }
        return ResponseEntity.notFound().build();
    }


}
