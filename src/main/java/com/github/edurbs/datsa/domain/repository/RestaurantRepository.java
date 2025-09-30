package com.github.edurbs.datsa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.edurbs.datsa.domain.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

    // @Query("""
    //         select case when count(1) > 0 then true else false end
    //         from Restaurant r
    //         join r.users u
    //         where r.id = :restaurantId
    //         and u.id = : userId
    //         """)
    // boolean userExists(Long restaurantId, Long userId);

    boolean existsByIdAndUsers_Id(Long restaurantId, Long userId);

}
