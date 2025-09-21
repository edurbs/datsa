package com.github.edurbs.datsa.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.UserController;
import com.github.edurbs.datsa.api.v1.dto.input.UserInput;
import com.github.edurbs.datsa.api.v1.dto.input.UserUpdateInput;
import com.github.edurbs.datsa.api.v1.dto.output.UserOutput;
import com.github.edurbs.datsa.domain.model.User;

@Component
public class UserMapper extends RepresentationModelAssemblerSupport<User, UserOutput> {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private LinksAdder linksAdder;

    public UserMapper() {
        super(UserController.class, UserOutput.class);
    }


    public User toDomain(UserInput inputModel) {
        return mapper.map(inputModel, User.class);
    }

    public void copyToDomain(UserUpdateInput userUpdateInput, User domainModel){
        mapper.map(userUpdateInput, domainModel);
    }


    public void copyToDomain(UserInput inputModel, User domainModel) {
        mapper.map(inputModel, domainModel);
    }

    @Override
    public @NonNull UserOutput toModel(@NonNull User domainModel) {
        UserOutput userOutput = createModelWithId(domainModel.getId(), domainModel);
        mapper.map(domainModel,userOutput);
        userOutput.add(linksAdder.toUsers("users"));
        userOutput.add(linksAdder.toUserGroups(domainModel.getId()));
        return userOutput;
    }

    @Override
    public @NonNull CollectionModel<UserOutput> toCollectionModel(@NonNull Iterable<? extends User> entities) {
        return super.toCollectionModel(entities)
            // add the link to the this in the bottom
            .add(linksAdder.toUsers());
    }

}
