package com.github.edurbs.datsa.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.exception.ProductNotFoundException;
import com.github.edurbs.datsa.domain.model.Product;
import com.github.edurbs.datsa.domain.repository.ProductRepository;

@Service
public class ProductRegistryService {

    private ProductRepository productRepository;

    public ProductRegistryService (ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Product getOne(Long id){
        return productRepository.findById(id)
                .orElseThrow(() ->  new ProductNotFoundException(id));
    }

    public Product getByRestaurant(Long restaurantId, Long productId){
        return productRepository.findByRestaurantIdAndId(restaurantId, productId)
                .orElseThrow(() -> new ModelNotFoundException("There is no product cod %d on the restaurant cod %d".formatted(productId, restaurantId)));
    }

    @Transactional
    public Product save(Product product){
        return productRepository.save(product);
    }

    @Transactional
    public void remove(Long id){
        if(notExists(id)){
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
        productRepository.flush();
    }

    private boolean notExists(Long id){
        return !productRepository.existsById(id);
    }
}
