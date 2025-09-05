package com.github.edurbs.datsa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.edurbs.datsa.domain.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


}
