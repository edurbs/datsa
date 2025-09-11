package com.github.edurbs.datsa.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.edurbs.datsa.domain.model.Product;
import com.github.edurbs.datsa.domain.model.ProductPhoto;
import com.github.edurbs.datsa.domain.model.Restaurant;


@Repository
public interface ProductRepository extends CustomJpaRepository<Product, Long>, ProductRepositoryQueries {

    List<Product> findByRestaurant(Restaurant restaurant);
    Optional<Product> findByRestaurantIdAndId(Long restaurantId, Long productId);
    List<Product> findByActiveTrueAndRestaurantId(Long restaurantId);

    @Query("from ProductPhoto where id = :productId")
    Optional<ProductPhoto> findPhotoById(@Param("productId") Long productId);
}
