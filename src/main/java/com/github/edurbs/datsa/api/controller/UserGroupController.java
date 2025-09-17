package com.github.edurbs.datsa.api.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.dto.output.GroupOutput;
import com.github.edurbs.datsa.api.mapper.GroupMapper;
import com.github.edurbs.datsa.domain.service.UserRegistryService;

import lombok.AllArgsConstructor;



@RestController
@RequestMapping("/users/{userId}/groups")
@AllArgsConstructor
public class UserGroupController {

    private GroupMapper groupMapper;
    private UserRegistryService userRegistryService;

    @GetMapping
    public CollectionModel<GroupOutput> getAll(@PathVariable Long userId) {
        return groupMapper.toCollectionModel(userRegistryService.getGroups(userId));
    }

    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable Long userId, @PathVariable Long groupId) {
        userRegistryService.associateGroup(userId, groupId);
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dissociate(@PathVariable Long userId, @PathVariable Long groupId){
        userRegistryService.dissociateGroup(userId, groupId);
    }


}
