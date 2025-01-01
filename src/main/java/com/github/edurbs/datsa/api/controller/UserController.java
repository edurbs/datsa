package com.github.edurbs.datsa.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.api.dto.input.UserInput;
import com.github.edurbs.datsa.api.dto.input.UserUpdateInput;
import com.github.edurbs.datsa.api.dto.output.UserOutput;
import com.github.edurbs.datsa.api.mapper.UserMapper;
import com.github.edurbs.datsa.domain.service.UserRegistryService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;





@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private UserRegistryService service;

    @GetMapping
    public List<UserOutput> getAll() {
        var users = service.getAll();
        return mapper.toOutputList(users);
    }

    @GetMapping("/{id}")
    public UserOutput getOne(@PathVariable Long id) {
        var user = service.getById(id);
        return mapper.toOutput(user);
    }

    @PostMapping
    public UserOutput add(@RequestBody @Valid UserInput userInput) {
        var domainUser = mapper.toDomain(userInput);
        var userSaved = service.save(domainUser);
        return mapper.toOutput(userSaved);
    }

    @PutMapping("/{id}")
    public UserOutput alter(@PathVariable Long id, @RequestBody @Valid UserUpdateInput userUpdateInput) {
        var domainUser = service.getById(id);
        mapper.copyToDomain(userUpdateInput, domainUser);
        var alteredUser = service.save(domainUser);
        return mapper.toOutput(alteredUser);
    }



}
