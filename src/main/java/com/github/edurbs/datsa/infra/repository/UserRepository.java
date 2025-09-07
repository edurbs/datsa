package com.github.edurbs.datsa.infra.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.github.edurbs.datsa.domain.model.User;

@Repository
public interface UserRepository extends CustomJpaRepository<User, Long>{

    Optional<User> findByEmail(String email);
}