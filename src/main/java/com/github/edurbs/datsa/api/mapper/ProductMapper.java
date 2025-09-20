package com.github.edurbs.datsa.api.mapper;

import java.util.Collection;
import java.util.List;

import com.github.edurbs.datsa.api.LinksAdder;
import com.github.edurbs.datsa.api.controller.RestaurantProductController;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.dto.input.ProductInput;
import com.github.edurbs.datsa.api.dto.output.ProductOutput;
import com.github.edurbs.datsa.domain.model.Product;

@Component
public class ProductMapper extends RepresentationModelAssemblerSupport<Product, ProductOutput> {

    private final ModelMapper modelMapper;
    private final LinksAdder linksAdder;

    public ProductMapper(ModelMapper modelMapper, LinksAdder linksAdder){
        super(RestaurantProductController.class, ProductOutput.class);
        this.modelMapper = modelMapper;
        this.linksAdder = linksAdder;
    }

    public Product toDomain(ProductInput inputModel) {
        return modelMapper.map(inputModel, Product.class);
    }


    public void copyToDomain(ProductInput inputModel, Product domainModel) {
        modelMapper.map(inputModel, domainModel);
    }

    @Override
    public ProductOutput toModel(Product entity) {
        Long productId = entity.getId();
        Long restaurantId = null;
        if( entity.getRestaurant() != null) {
            restaurantId = entity.getRestaurant().getId();
        }
        ProductOutput model;
        if(restaurantId !=null && productId !=null){
            model = createModelWithId(productId, entity, restaurantId);
        }else{
            model = instantiateModel(entity);
        }
        modelMapper.map(entity, model);
        model.add(linksAdder.toProducts(restaurantId));
        return model;
    }

}
