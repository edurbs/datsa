package com.github.edurbs.datsa.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.github.edurbs.datsa.domain.model.MyUser;

@Repository
public interface UserRepository extends CustomJpaRepository<MyUser, Long>{

    Optional<MyUser> findByEmail(String email);
}