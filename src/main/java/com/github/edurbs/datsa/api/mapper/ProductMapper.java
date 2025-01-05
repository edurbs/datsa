package com.github.edurbs.datsa.api.mapper;

import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.dto.input.ProductInput;
import com.github.edurbs.datsa.api.dto.output.ProductOutput;
import com.github.edurbs.datsa.domain.model.Product;

@Component
public class ProductMapper implements IMapper<Product, ProductInput, ProductOutput> {

    private ModelMapper modelMapper;
    public ProductMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public Product toDomain(ProductInput inputModel) {
        return modelMapper.map(inputModel, Product.class);
    }

    @Override
    public void copyToDomain(ProductInput inputModel, Product domainModel) {
        modelMapper.map(inputModel, domainModel);
    }

    @Override
    public ProductOutput toOutput(Product domainModel) {
        return modelMapper.map(domainModel, ProductOutput.class);
    }

    @Override
    public List<ProductOutput> toOutputList(Collection<Product> domainModels) {
        return domainModels.stream()
            .map(this::toOutput)
            .toList();
    }

}
