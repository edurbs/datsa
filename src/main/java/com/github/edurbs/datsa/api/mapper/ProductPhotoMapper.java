package com.github.edurbs.datsa.api.mapper;

import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.dto.input.ProductPhotoInput;
import com.github.edurbs.datsa.api.dto.output.ProductPhotoOutput;
import com.github.edurbs.datsa.domain.model.ProductPhoto;

@Component
public class ProductPhotoMapper implements IMapper<ProductPhoto, ProductPhotoInput, ProductPhotoOutput> {

    @Autowired
    private ModelMapper modelMapper;


	@Override
	public ProductPhoto toDomain(ProductPhotoInput inputModel) {
		return modelMapper.map(inputModel, ProductPhoto.class);
	}

	@Override
	public void copyToDomain(ProductPhotoInput inputModel, ProductPhoto domainModel) {
		modelMapper.map(inputModel, domainModel);
	}

	@Override
	public ProductPhotoOutput toOutput(ProductPhoto domainModel) {
		return modelMapper.map(domainModel, ProductPhotoOutput.class);
	}

	@Override
	public List<ProductPhotoOutput> toOutputList(Collection<ProductPhoto> domainModels) {
		return domainModels.stream()
            .map(this::toOutput)
            .toList();
	}


}
