package com.github.edurbs.datsa.api.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.dto.input.UserInput;
import com.github.edurbs.datsa.api.dto.input.UserUpdateInput;
import com.github.edurbs.datsa.api.dto.output.UserOutput;
import com.github.edurbs.datsa.domain.model.User;

@Component
public class UserMapper implements IMapper<User, UserInput, UserOutput> {

    @Autowired
    private ModelMapper mapper;

    @Override
    public User toDomain(UserInput inputModel) {
        return mapper.map(inputModel, User.class);
    }

    public void copyToDomain(UserUpdateInput userUpdateInput, User domainModel){
        mapper.map(userUpdateInput, domainModel);
    }

    @Override
    public void copyToDomain(UserInput inputModel, User domainModel) {
        mapper.map(inputModel, domainModel);
    }

    @Override
    public UserOutput toOutput(User domainModel) {
        return mapper.map(domainModel, UserOutput.class);
    }

    @Override
    public List<UserOutput> toOutputList(List<User> domainModels) {
        return domainModels.stream()
                .map(this::toOutput)
                .toList();
    }

}
