package com.github.edurbs.datsa.domain.repository.spec;

import com.github.edurbs.datsa.domain.filter.OrderFilter;
import com.github.edurbs.datsa.domain.model.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class OrderSpecs {

    private OrderSpecs(){}

    public static Specification<Order> withFilter(OrderFilter filter){
        return (root, query, builder) -> {
            if (query != null && Order.class.equals(query.getResultType())) { // to fix exception when sql count
                root.fetch("restaurant"); // to fix N+1 problem
                root.fetch("user");
            }

            var predicates = new ArrayList<Predicate>();
            if(filter.getUserId()!=null){
                predicates.add(
                    builder.equal(
                        root.get("user").get("id"),
                        filter.getUserId())
                );
            }
            if(filter.getRestaurantId()!=null){
                predicates.add(
                    builder.equal(
                        root.get("restaurant").get("id"),
                        filter.getRestaurantId())
                );
            }
            if(filter.getBeginCreationDate()!=null){
                predicates.add(
                    builder.greaterThanOrEqualTo(
                        root.get("creationDate"),
                        filter.getBeginCreationDate())
                );
            }
            if(filter.getEndCreationDate()!=null){
                predicates.add(
                    builder.lessThanOrEqualTo(
                        root.get("creationDate"),
                        filter.getEndCreationDate())
                );
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
