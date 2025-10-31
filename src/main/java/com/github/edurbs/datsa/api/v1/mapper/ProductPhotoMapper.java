package com.github.edurbs.datsa.api.v1.mapper;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.RestaurantProductPhotoController;
import com.github.edurbs.datsa.api.v1.dto.input.ProductPhotoInput;
import com.github.edurbs.datsa.api.v1.dto.output.ProductPhotoOutput;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.ProductPhoto;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductPhotoMapper extends RepresentationModelAssemblerSupport<ProductPhoto, ProductPhotoOutput> {

    private final ModelMapper modelMapper;
    private final LinksAdder linksAdder;
    private final MySecurity mySecurity;

    public ProductPhotoMapper(ModelMapper modelMapper, LinksAdder linksAdder, MySecurity mySecurity) {
        super(RestaurantProductPhotoController.class, ProductPhotoOutput.class );
        this.modelMapper = modelMapper;
        this.linksAdder = linksAdder;
        this.mySecurity = mySecurity;
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
        if(this.mySecurity.canConsultRestaurants()){
            model.add(linksAdder.toProductPhoto(entity.getRestaurantId(), entity.getProduct().getId()));
            model.add(linksAdder.toProductPhoto(entity.getRestaurantId(), entity.getProduct().getId(), "product"));
        }
        return model;
	}



}
