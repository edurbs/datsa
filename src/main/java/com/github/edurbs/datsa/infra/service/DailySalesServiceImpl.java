package com.github.edurbs.datsa.infra.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.github.edurbs.datsa.api.dto.DailySales;
import com.github.edurbs.datsa.domain.filter.DailySalesFilter;
import com.github.edurbs.datsa.domain.model.Order;
import com.github.edurbs.datsa.domain.model.OrderStatus;
import com.github.edurbs.datsa.domain.service.DailySalesService;

@Repository
public class DailySalesServiceImpl implements DailySalesService {

    @PersistenceContext
    private EntityManager manager;

    private static final String CREATION_DATE = "creationDate";

    @Override
    public List<DailySales> getDailySales(DailySalesFilter filter, String timeOffset) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(DailySales.class);
        var root = query.from(Order.class);
        var functionConvertTzCreationDate = builder.function(
            "convert_tz",
            Date.class,
            root.get(CREATION_DATE),
            builder.literal("+00:00"),
            builder.literal(timeOffset)
        );
        var functionDateForCreationDate = builder.function(
                "date",
                Date.class,
                functionConvertTzCreationDate);
        var selection = builder.construct(DailySales.class,
            functionDateForCreationDate,
            builder.count(root.get("id")),
            builder.sum(root.get("totalAmount"))
        );
        var predicates = new ArrayList<Predicate>();
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
                    root.get(CREATION_DATE),
                    filter.getBeginCreationDate())
            );
        }
        if(filter.getEndCreationDate()!=null){
            predicates.add(
                builder.lessThanOrEqualTo(
                    root.get(CREATION_DATE),
                    filter.getEndCreationDate())
            );
        }
        predicates.add(root.get("status").in(OrderStatus.CONFIRMED, OrderStatus.DELIVERED));
        query.where(predicates.toArray(new Predicate[0]));
        query.select(selection);
        query.groupBy(functionDateForCreationDate);

        return manager.createQuery(query).getResultList();
    }

}
