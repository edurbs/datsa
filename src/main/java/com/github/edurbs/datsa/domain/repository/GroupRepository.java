package com.github.edurbs.datsa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.edurbs.datsa.domain.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}
