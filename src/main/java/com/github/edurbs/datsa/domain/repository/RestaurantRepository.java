package com.github.edurbs.datsa.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.github.edurbs.datsa.domain.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

    @Query("from Restaurant r join fetch r.kitchens left join fetch r.paymentMethods")
    @NonNull List<Restaurant> findAll();

}
