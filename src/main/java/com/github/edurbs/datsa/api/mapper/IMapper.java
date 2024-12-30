package com.github.edurbs.datsa.api.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.edurbs.datsa.api.dto.input.InputModel;
import com.github.edurbs.datsa.api.dto.output.OutputModel;
import com.github.edurbs.datsa.domain.model.DomainModel;

public interface IMapper <D extends DomainModel, I extends InputModel, O extends OutputModel> {


    @Autowired
    public D toDomain(I inputModel);
    public void copyToDomain(I inputModel, D domainModel);
    public O toOutput(D domainModel);
    public List<O> toOutputList(List<D> domainModels);
}
