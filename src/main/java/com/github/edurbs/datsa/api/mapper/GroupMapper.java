package com.github.edurbs.datsa.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.LinksAdder;
import com.github.edurbs.datsa.api.controller.GroupController;
import com.github.edurbs.datsa.api.dto.input.GroupInput;
import com.github.edurbs.datsa.api.dto.output.GroupOutput;
import com.github.edurbs.datsa.domain.model.Group;

@Component
public class GroupMapper extends RepresentationModelAssemblerSupport<Group, GroupOutput>{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdder linksAdder;

    public GroupMapper(){

        super(GroupController.class, GroupOutput.class);
    }

    public Group toDomain(GroupInput inputModel) {

        return modelMapper.map(inputModel, Group.class);
    }


    public void copyToDomain(GroupInput inputModel, Group domainModel) {
        modelMapper.map(inputModel, domainModel);
    }

    @Override
    public @NonNull GroupOutput toModel(@NonNull Group entity) {
        GroupOutput groupOutput = createModelWithId(entity.getId(), entity);
        modelMapper.map(entity, groupOutput);
        groupOutput.add(linksAdder.toGroups("groups"));
        groupOutput.add(linksAdder.toPermissions(entity.getId(), "permissions"));
        return groupOutput;
    }

//    @Override
//    public CollectionModel<GroupOutput> toCollectionModel(
//            Iterable<? extends Group> entities) {
//        return super.toCollectionModel(entities).add(linksAdder.toGroups());
//    }



}
