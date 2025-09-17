package com.github.edurbs.datsa.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.controller.UserController;
import com.github.edurbs.datsa.api.dto.input.UserInput;
import com.github.edurbs.datsa.api.dto.input.UserUpdateInput;
import com.github.edurbs.datsa.api.dto.output.UserOutput;
import com.github.edurbs.datsa.domain.model.User;

@Component
public class UserMapper extends RepresentationModelAssemblerSupport<User, UserOutput> {

    @Autowired
    private ModelMapper mapper;


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
        return userOutput;
    }

    @Override
    public @NonNull CollectionModel<UserOutput> toCollectionModel(@NonNull Iterable<? extends User> entities) {
        return super.toCollectionModel(entities)
            // add the link to the this in the bottom
            .add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).getAll()
            ).withRel("users"));
    }

}
