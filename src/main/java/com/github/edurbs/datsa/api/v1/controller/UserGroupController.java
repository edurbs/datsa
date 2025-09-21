package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.LinksAdder;
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

import com.github.edurbs.datsa.api.v1.dto.output.GroupOutput;
import com.github.edurbs.datsa.api.v1.mapper.GroupMapper;
import com.github.edurbs.datsa.domain.service.UserRegistryService;

import lombok.AllArgsConstructor;



@RestController
@RequestMapping("/users/{userId}/groups")
@AllArgsConstructor
public class UserGroupController {

    private final GroupMapper groupMapper;
    private final UserRegistryService userRegistryService;
    private final LinksAdder linksAdder;

    @GetMapping
    public CollectionModel<GroupOutput> getAll(@PathVariable Long userId) {
        CollectionModel<GroupOutput>  groupOutputCollectionModel = groupMapper.toCollectionModel(userRegistryService.getGroups(userId));
        groupOutputCollectionModel.getContent().forEach(groupOutput -> {
            groupOutput.add(linksAdder.toAssociateGroup(userId, groupOutput.getId(), "associate"));
        });
        groupOutputCollectionModel.add(linksAdder.toDissociateGroup(userId, "dissociate"));
        return groupOutputCollectionModel;
    }

    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associate(@PathVariable Long userId, @PathVariable Long groupId) {
        userRegistryService.associateGroup(userId, groupId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> dissociate(@PathVariable Long userId, @PathVariable Long groupId){
        userRegistryService.dissociateGroup(userId, groupId);
        return ResponseEntity.noContent().build();
    }


}
