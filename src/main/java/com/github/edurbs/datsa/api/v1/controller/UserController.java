package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.dto.input.UserInput;
import com.github.edurbs.datsa.api.v1.dto.input.UserPasswordInput;
import com.github.edurbs.datsa.api.v1.dto.input.UserUpdateInput;
import com.github.edurbs.datsa.api.v1.dto.output.UserOutput;
import com.github.edurbs.datsa.api.v1.mapper.UserMapper;
import com.github.edurbs.datsa.api.v1.openapi.controller.UserControllerOpenApi;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.domain.model.MyUser;
import com.github.edurbs.datsa.domain.service.UserRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController implements UserControllerOpenApi {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private UserRegistryService service;

    @CheckSecurity.UsersGroupsPermissions.CanConsult
    @GetMapping
    @Override
    public CollectionModel<UserOutput> getAll() {
        List<MyUser> users = service.getAll();
        return mapper.toCollectionModel(users);
    }

    @CheckSecurity.UsersGroupsPermissions.CanConsult
    @GetMapping("/{id}")
    @Override
    public UserOutput getOne(@PathVariable Long id) {
        MyUser user = service.getById(id);
        return mapper.toModel(user);
    }

    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public UserOutput add(@RequestBody @Valid UserInput userInput) {
        MyUser domainUser = mapper.toDomain(userInput);
        MyUser userSaved = service.save(domainUser);
        return mapper.toModel(userSaved);
    }

    @CheckSecurity.UsersGroupsPermissions.CanEditUser
    @PutMapping("/{userId}")
    @Override
    public UserOutput alter(@PathVariable Long userId, @RequestBody @Valid UserUpdateInput userUpdateInput) {
        MyUser domainUser = service.getById(userId);
        mapper.copyToDomain(userUpdateInput, domainUser);
        MyUser alteredUser = service.save(domainUser);
        return mapper.toModel(alteredUser);
    }

    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void remove(@PathVariable Long userId){
        service.remove(userId);
    }

    @CheckSecurity.UsersGroupsPermissions.CanEditOwnPassword
    @PutMapping("/{userId}/password")
    @Override
    public UserOutput alterPassword(@PathVariable Long userId, @RequestBody @Valid UserPasswordInput userPasswordInput) {
        var domainUser = service.changePassword(userId, userPasswordInput.getOldPassword(), userPasswordInput.getNewPassword());
        return mapper.toModel(domainUser);
    }

}
