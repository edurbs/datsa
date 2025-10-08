package com.github.edurbs.datsa.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.PermissionController;
import com.github.edurbs.datsa.api.v1.dto.input.PermissionInput;
import com.github.edurbs.datsa.api.v1.dto.output.PermissionOutput;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.Permission;

@Component
public class PermissionMapper extends RepresentationModelAssemblerSupport<Permission, PermissionOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdder linksAdder;

    @Autowired
    private MySecurity mySecurity;

    public PermissionMapper() {
        super(PermissionController.class, PermissionOutput.class);
    }

    public Permission toDomain(PermissionInput inputModel) {
        return modelMapper.map(inputModel, Permission.class);
    }

    public void copyToDomain(PermissionInput inputModel, Permission domainModel) {
        modelMapper.map(inputModel, domainModel);
    }

    @Override
    public @NonNull PermissionOutput toModel(@NonNull Permission domainModel) {
        return modelMapper.map(domainModel, PermissionOutput.class);
    }

    @Override
    public @NonNull CollectionModel<PermissionOutput> toCollectionModel(@NonNull Iterable<? extends Permission> entities) {
        var collectionModel = super.toCollectionModel(entities);
        if(this.mySecurity.canConsultUsersGroupsPermissions()){
            collectionModel.add(linksAdder.toPermissions());
        }
        return collectionModel;
    }


}
