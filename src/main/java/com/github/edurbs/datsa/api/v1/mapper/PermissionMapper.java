package com.github.edurbs.datsa.api.v1.mapper;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.PermissionController;
import com.github.edurbs.datsa.api.v1.dto.input.PermissionInput;
import com.github.edurbs.datsa.api.v1.dto.output.PermissionOutput;
import com.github.edurbs.datsa.domain.model.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper extends RepresentationModelAssemblerSupport<Permission, PermissionOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdder linksAdder;

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
    public PermissionOutput toModel(Permission domainModel) {
        return modelMapper.map(domainModel, PermissionOutput.class);
    }
}
