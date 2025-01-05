package com.github.edurbs.datsa.api.mapper;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.dto.input.PermissionInput;
import com.github.edurbs.datsa.api.dto.output.PermissionOutput;
import com.github.edurbs.datsa.domain.model.Permission;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PermissionMapper implements IMapper<Permission, PermissionInput, PermissionOutput> {

    private ModelMapper modelMapper;

    @Override
    public Permission toDomain(PermissionInput inputModel) {
        return modelMapper.map(inputModel, Permission.class);
    }

    @Override
    public void copyToDomain(PermissionInput inputModel, Permission domainModel) {
        modelMapper.map(inputModel, domainModel);
    }

    @Override
    public PermissionOutput toOutput(Permission domainModel) {
        return modelMapper.map(domainModel, PermissionOutput.class);
    }

    @Override
    public Set<PermissionOutput> toOutputList(Collection<Permission> domainModels) {
        return domainModels.stream()
                .map(this::toOutput)
                .collect(Collectors.toSet());
    }

}
