package com.github.edurbs.datsa.api.mapper;

import java.util.Collection;
import java.util.List;

import com.github.edurbs.datsa.api.LinksAdder;
import com.github.edurbs.datsa.api.controller.RestaurantProductPhotoController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.dto.input.ProductPhotoInput;
import com.github.edurbs.datsa.api.dto.output.ProductPhotoOutput;
import com.github.edurbs.datsa.domain.model.ProductPhoto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductPhotoMapper extends RepresentationModelAssemblerSupport<ProductPhoto, ProductPhotoOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdder linksAdder;

    public ProductPhotoMapper() {
        super(RestaurantProductPhotoController.class, ProductPhotoOutput.class );
    }

    public ProductPhoto toDomain(ProductPhotoInput inputModel) {
		return modelMapper.map(inputModel, ProductPhoto.class);
	}

	public void copyToDomain(ProductPhotoInput inputModel, ProductPhoto domainModel) {
		modelMapper.map(inputModel, domainModel);
	}

    @Override
	public ProductPhotoOutput toModel(ProductPhoto entity) {
        ProductPhotoOutput model = modelMapper.map(entity, ProductPhotoOutput.class);
        model.add(linksAdder.toProductPhoto(entity.getRestaurantId(), entity.getProduct().getId()));
        model.add(linksAdder.toProductPhoto(entity.getRestaurantId(), entity.getProduct().getId(), "product"));
        return model;
	}



}
