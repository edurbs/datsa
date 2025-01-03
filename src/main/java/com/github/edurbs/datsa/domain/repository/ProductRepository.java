package com.github.edurbs.datsa.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.github.edurbs.datsa.domain.model.Product;
import com.github.edurbs.datsa.domain.model.Restaurant;


@Repository
public interface ProductRepository extends CustomJpaRepository<Product, Long> {

    List<Product> findByRestaurant(Restaurant restaurant);
    Optional<Product> findByRestaurantIdAndId(Long restaurantId, Long productId);
}
