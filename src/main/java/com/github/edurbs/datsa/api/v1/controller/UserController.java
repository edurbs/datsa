package com.github.edurbs.datsa.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.github.edurbs.datsa.api.v1.dto.input.UserInput;
import com.github.edurbs.datsa.api.v1.dto.input.UserPasswordInput;
import com.github.edurbs.datsa.api.v1.dto.input.UserUpdateInput;
import com.github.edurbs.datsa.api.v1.dto.output.UserOutput;
import com.github.edurbs.datsa.api.v1.mapper.UserMapper;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.domain.model.User;
import com.github.edurbs.datsa.domain.service.UserRegistryService;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private UserRegistryService service;

    @CheckSecurity.UsersGroupsPermissions.CanConsult
    @GetMapping
    public CollectionModel<UserOutput> getAll() {
        List<User> users = service.getAll();
        return mapper.toCollectionModel(users);
    }

    @CheckSecurity.UsersGroupsPermissions.CanConsult
    @GetMapping("/{id}")
    public UserOutput getOne(@PathVariable Long id) {
        User user = service.getById(id);
        return mapper.toModel(user);
    }

    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutput add(@RequestBody @Valid UserInput userInput) {
        User domainUser = mapper.toDomain(userInput);
        User userSaved = service.save(domainUser);
        return mapper.toModel(userSaved);
    }

    @CheckSecurity.UsersGroupsPermissions.CanEditUser
    @PutMapping("/{id}")
    public UserOutput alter(@PathVariable Long id, @RequestBody @Valid UserUpdateInput userUpdateInput) {
        User domainUser = service.getById(id);
        mapper.copyToDomain(userUpdateInput, domainUser);
        User alteredUser = service.save(domainUser);
        return mapper.toModel(alteredUser);
    }

    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id){
        service.remove(id);
    }

    @CheckSecurity.UsersGroupsPermissions.CanEditOwnPassword
    @PutMapping("/{userId}/password")
    public UserOutput alterPassword(@PathVariable Long userId, @RequestBody @Valid UserPasswordInput userPasswordInput) {
        var domainUser = service.changePassword(userId, userPasswordInput.getOldPassword(), userPasswordInput.getNewPassword());
        return mapper.toModel(domainUser);
    }

}
