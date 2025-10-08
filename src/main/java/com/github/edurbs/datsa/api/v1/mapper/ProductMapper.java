package com.github.edurbs.datsa.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.RestaurantProductController;
import com.github.edurbs.datsa.api.v1.dto.input.ProductInput;
import com.github.edurbs.datsa.api.v1.dto.output.ProductOutput;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.Product;

@Component
public class ProductMapper extends RepresentationModelAssemblerSupport<Product, ProductOutput> {

    private final ModelMapper modelMapper;
    private final LinksAdder linksAdder;
    private final MySecurity mySecurity;

    public ProductMapper(ModelMapper modelMapper, LinksAdder linksAdder, MySecurity mySecurity){
        super(RestaurantProductController.class, ProductOutput.class);
        this.modelMapper = modelMapper;
        this.linksAdder = linksAdder;
        this.mySecurity = mySecurity;
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
        if(this.mySecurity.canConsultRestaurants()){
            model.add(linksAdder.toProducts(restaurantId, "products"));
            model.add(linksAdder.toProductPhoto(restaurantId, productId, "photo"));
        }
        return model;
    }

}
