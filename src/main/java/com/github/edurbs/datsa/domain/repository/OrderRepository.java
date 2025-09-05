package com.github.edurbs.datsa.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.edurbs.datsa.domain.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("from Order o join fetch o.user join fetch o.restaurant r join fetch r.kitchen")
    List<Order> findAll();

}
