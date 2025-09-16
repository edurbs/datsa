package com.github.edurbs.datsa.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.github.edurbs.datsa.domain.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @EntityGraph(attributePaths = {"state"}) // fix problem n+1
    @NonNull List<City> findAll();

    Long countByStateId(Long stateId);


}
