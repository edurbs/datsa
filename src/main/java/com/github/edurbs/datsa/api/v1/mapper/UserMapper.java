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
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.MyUser;

@Component
public class UserMapper extends RepresentationModelAssemblerSupport<MyUser, UserOutput> {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private LinksAdder linksAdder;

    @Autowired
    private MySecurity mySecurity;

    public UserMapper() {
        super(UserController.class, UserOutput.class);
    }


    public MyUser toDomain(UserInput inputModel) {
        return mapper.map(inputModel, MyUser.class);
    }

    public void copyToDomain(UserUpdateInput userUpdateInput, MyUser domainModel){
        mapper.map(userUpdateInput, domainModel);
    }


    public void copyToDomain(UserInput inputModel, MyUser domainModel) {
        mapper.map(inputModel, domainModel);
    }

    @Override
    public @NonNull UserOutput toModel(@NonNull MyUser domainModel) {
        UserOutput userOutput = createModelWithId(domainModel.getId(), domainModel);
        mapper.map(domainModel,userOutput);
        if(this.mySecurity.canConsultUsersGroupsPermissions()){
            userOutput.add(linksAdder.toUsers("users"));
            userOutput.add(linksAdder.toUserGroups(domainModel.getId()));
        }
        return userOutput;
    }

    @Override
    public @NonNull CollectionModel<UserOutput> toCollectionModel(@NonNull Iterable<? extends MyUser> entities) {
        return super.toCollectionModel(entities)
            // add the link to the this in the bottom
            .add(linksAdder.toUsers());
    }

}
