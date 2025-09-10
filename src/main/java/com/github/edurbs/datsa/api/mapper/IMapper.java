package com.github.edurbs.datsa.api.mapper;

import java.util.Collection;

import com.github.edurbs.datsa.api.dto.input.InputModel;
import com.github.edurbs.datsa.api.dto.output.OutputModel;
import com.github.edurbs.datsa.domain.model.DomainModel;

public interface IMapper <D extends DomainModel, I extends InputModel, O extends OutputModel> {


    public D toDomain(I inputModel);
    public void copyToDomain(I inputModel, D domainModel);
    public O toOutput(D domainModel);
    public Collection<O> toOutputList(Collection<D> domainModels);
}
