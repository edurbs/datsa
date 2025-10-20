package com.github.edurbs.datsa.infra.service.query;

import com.github.edurbs.datsa.api.v1.dto.DailySales;
import com.github.edurbs.datsa.domain.filter.DailySalesFilter;
import com.github.edurbs.datsa.domain.model.Order;
import com.github.edurbs.datsa.domain.model.OrderStatus;
import com.github.edurbs.datsa.domain.model.Restaurant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailySalesServiceImplTest {

    @Mock
    EntityManager manager;

    @Mock
    CriteriaBuilder criteriaBuilder;

    @Mock
    CriteriaQuery<DailySales> criteriaQuery;

    @Mock
    Root<Order> orderRoot;

    @Mock
    Expression<Date> dateExpression;

    @Mock
    CompoundSelection<DailySales> compoundSelection;

    @Mock
    Predicate predicate;

    @Mock
    Path<Long> pathLong;

    @Mock
    Path<OffsetDateTime> pathOffsetDateTime;

    @Mock
    Path<OrderStatus> pathOrderStatus;

    @Mock
    Path<Restaurant> pathRestaurant;

    @Mock
    Expression<Number> numberExpression;

    @Mock
    Expression<String> stringExpression;

    @Mock
    Expression<Long> longExpression;

    @Mock
    Path<Number> pathNumber;

    @Mock
    TypedQuery<DailySales> typedQuery;

    @InjectMocks
    DailySalesServiceImpl sut;

    DailySalesFilter filter;

    private DailySales getDaylySales() {
        return new DailySales(
                java.sql.Date.valueOf(LocalDate.now()),
                100L,
                new BigDecimal(100)
        );
    }

    @SuppressWarnings("unchecked")
    private void mock() {
        when(manager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(DailySales.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Order.class)).thenReturn(orderRoot);
        when(criteriaBuilder.function(
                any(String.class),
                any(Class.class),
                any(Expression.class),
                any(Expression.class),
                any(Expression.class)
        )).thenReturn(dateExpression);
        when(criteriaBuilder.function(
                any(String.class),
                any(Class.class),
                any(Expression.class)
        )).thenReturn(dateExpression);
        when(criteriaBuilder.<String>literal(any())).thenReturn(stringExpression);
        when(criteriaBuilder.construct(
                any(Class.class),
                any(Expression.class),
                any(Expression.class),
                any(Expression.class)
        )).thenReturn(compoundSelection);
        if (filter.getRestaurantId() != null) {
            when(criteriaBuilder.equal(
                    any(Expression.class),
                    any(Object.class)
            )).thenReturn(predicate);
            when(orderRoot.<Restaurant>get("restaurant")).thenReturn(pathRestaurant);
            when(pathRestaurant.<Long>get("id")).thenReturn(pathLong);
        }
        if (filter.getCreationDateFrom() != null) {
            when(criteriaBuilder.greaterThanOrEqualTo(
                    pathOffsetDateTime,
                    filter.getCreationDateFrom()
            )).thenReturn(predicate);
        }
        if (filter.getCreationDateTo() != null) {
            when(criteriaBuilder.lessThanOrEqualTo(
                    pathOffsetDateTime,
                    filter.getCreationDateTo()
            )).thenReturn(predicate);
        }
        when(orderRoot.<OffsetDateTime>get("creationDate")).thenReturn(pathOffsetDateTime);
        when(orderRoot.<OrderStatus>get("status")).thenReturn(pathOrderStatus);
        when(pathOrderStatus.in(OrderStatus.CONFIRMED, OrderStatus.DELIVERED)).thenReturn(predicate);
        when(criteriaQuery.where(any(Predicate[].class))).thenReturn(criteriaQuery);
        when(criteriaQuery.select(any(CompoundSelection.class))).thenReturn(criteriaQuery);
        when(criteriaQuery.groupBy(any(Expression.class))).thenReturn(criteriaQuery);
        when(orderRoot.<Long>get("id")).thenReturn(pathLong);
        when(criteriaBuilder.count(any(Expression.class))).thenReturn(longExpression);
        when(orderRoot.<Number>get("totalAmount")).thenReturn(pathNumber);
        when(criteriaBuilder.sum(any(Expression.class))).thenReturn(numberExpression);
        when(manager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of());
    }

    @Test
    void givenFilterAndOffset_whenGetDailySales_thenReturnListOfDailySales() {
        // Arrange
        filter = new DailySalesFilter();
        filter.setRestaurantId(1L);
        filter.setCreationDateFrom(OffsetDateTime.now().minusDays(1));
        filter.setCreationDateTo(OffsetDateTime.now());
        String timeOffset = "+00:00";

        mock();

        // Act
        List<DailySales> result = sut.getDailySales(filter, timeOffset);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    void givenFilterWithOnlyRestaurantId_whenGetDailySales_thenReturnFilteredResults() {
        // Arrange
        filter = new DailySalesFilter();
        filter.setRestaurantId(1L);
        String timeOffset = "+00:00";

        mock();
        reset(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(getDaylySales()));

        // Act
        List<DailySales> result = sut.getDailySales(filter, timeOffset);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void givenFilterWithOnlyCreationDateFrom_whenGetDailySales_thenReturnFilteredResults() {
        // Arrange
        filter = new DailySalesFilter();
        filter.setCreationDateFrom(OffsetDateTime.now().minusDays(1));
        String timeOffset = "+00:00";

        mock();

        // Act
        List<DailySales> result = sut.getDailySales(filter, timeOffset);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void givenFilterWithOnlyCreationDateTo_whenGetDailySales_thenReturnFilteredResults() {
        // Arrange
        filter = new DailySalesFilter();
        filter.setCreationDateTo(OffsetDateTime.now());
        String timeOffset = "+00:00";

        mock();

        // Act
        List<DailySales> result = sut.getDailySales(filter, timeOffset);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    void givenNoFilter_whenGetDailySales_thenReturnAllResults() {
        // Arrange
        filter = new DailySalesFilter();
        String timeOffset = "+00:00";

        mock();
        reset(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(getDaylySales(), getDaylySales()));

        // Act
        List<DailySales> result = sut.getDailySales(filter, timeOffset);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}