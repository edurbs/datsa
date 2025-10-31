package com.github.edurbs.datsa.domain.repository;

import com.github.edurbs.datsa.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

    // This query do this in plain sql:
    //         select case when count(1) > 0 then true else false end
    //         from Restaurant r
    //         join r.users u
    //         where r.id = :restaurantId
    //         and u.id = : userId
    boolean existsByIdAndUsers_Id(Long restaurantId, Long userId);

}
