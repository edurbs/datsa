package com.github.edurbs.datsa.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.edurbs.datsa.domain.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("from Order o join fetch o.user join fetch o.restaurant r join fetch r.kitchen")
    List<Order> findAll();

    Optional<Order> findByUuid(String uuid);

}
