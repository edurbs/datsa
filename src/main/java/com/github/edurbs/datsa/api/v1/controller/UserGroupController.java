package com.github.edurbs.datsa.api.v1.controller;

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

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.dto.output.GroupOutput;
import com.github.edurbs.datsa.api.v1.mapper.GroupMapper;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.service.UserRegistryService;

import lombok.AllArgsConstructor;



@RestController
@RequestMapping("/v1/users/{userId}/groups")
@AllArgsConstructor
public class UserGroupController {

    private final GroupMapper groupMapper;
    private final UserRegistryService userRegistryService;
    private final LinksAdder linksAdder;
    private final MySecurity mySecurity;

    @CheckSecurity.UsersGroupsPermissions.CanConsult
    @GetMapping
    public CollectionModel<GroupOutput> getAll(@PathVariable Long userId) {
        CollectionModel<GroupOutput>  groupOutputCollectionModel = groupMapper.toCollectionModel(userRegistryService.getGroups(userId));
        groupOutputCollectionModel.removeLinks();
        if(this.mySecurity.canEditUsersGroupsPermissions()){
            groupOutputCollectionModel.getContent().forEach(groupOutput -> {
                groupOutput.add(linksAdder.toDissociateGroup(userId, groupOutput.getId(), "dissociate"));
            });
            groupOutputCollectionModel.add(linksAdder.toAssociateGroup(userId, "associate"));
        }
        return groupOutputCollectionModel;
    }

    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associate(@PathVariable Long userId, @PathVariable Long groupId) {
        userRegistryService.associateGroup(userId, groupId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> dissociate(@PathVariable Long userId, @PathVariable Long groupId){
        userRegistryService.dissociateGroup(userId, groupId);
        return ResponseEntity.noContent().build();
    }


}
