package com.github.edurbs.datsa.api.v1.mapper;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.GroupController;
import com.github.edurbs.datsa.api.v1.dto.input.GroupInput;
import com.github.edurbs.datsa.api.v1.dto.output.GroupOutput;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper extends RepresentationModelAssemblerSupport<Group, GroupOutput> {

    private final ModelMapper modelMapper;

    private final LinksAdder linksAdder;

    private final MySecurity mySecurity;

    public GroupMapper(ModelMapper modelMapper, LinksAdder linksAdder, MySecurity mySecurity) {
        super(GroupController.class, GroupOutput.class);
        this.modelMapper = modelMapper;
        this.linksAdder = linksAdder;
        this.mySecurity = mySecurity;
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
        if (this.mySecurity.canConsultUsersGroupsPermissions()) {
            groupOutput.add(linksAdder.toGroups("groups"));
            groupOutput.add(linksAdder.toPermissions(entity.getId(), "permissions"));

        }

        return groupOutput;
    }

    @Override
    public @NonNull CollectionModel<GroupOutput> toCollectionModel(@NonNull Iterable<? extends Group> entities) {
        CollectionModel<GroupOutput> collectionModel = super.toCollectionModel(entities);
        if(this.mySecurity.canConsultUsersGroupsPermissions()){
            collectionModel.add(linksAdder.toGroups());
        }
        return collectionModel;
    }

}
