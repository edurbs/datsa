package com.github.edurbs.datsa.api.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.dto.input.GroupInput;
import com.github.edurbs.datsa.api.dto.output.GroupOutput;
import com.github.edurbs.datsa.domain.model.Group;

@Component
public class GroupMapper implements IMapper<Group, GroupInput, GroupOutput>{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Group toDomain(GroupInput inputModel) {
        return modelMapper.map(inputModel, Group.class);
    }

    @Override
    public void copyToDomain(GroupInput inputModel, Group domainModel) {
        modelMapper.map(inputModel, domainModel);
    }

    @Override
    public GroupOutput toOutput(Group domainModel) {
        return modelMapper.map(domainModel, GroupOutput.class);
    }

    @Override
    public List<GroupOutput> toOutputList(List<Group> domainModels) {
        return domainModels.stream()
            .map(this::toOutput)
            .toList();
    }


}
