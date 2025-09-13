package com.github.edurbs.datsa.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.github.edurbs.datsa.domain.exception.ModelValidationException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="`order`")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Order implements DomainModel {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal totalAmount;

    @CreationTimestamp
    private OffsetDateTime creationDate;

    private OffsetDateTime confirmationDate;

    private OffsetDateTime cancellationDate;

    private OffsetDateTime deliveryDate;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "customer_user_id",nullable = false)
    @ToString.Include
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    public void calcTotalAmount(){
        getItems().forEach(OrderItem::calcTotalAmount);
        this.subtotal = getItems().stream()
                .map(item -> item.getTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalAmount = this.subtotal.add(this.shippingFee);
    }

    public void setShippingFee(){
        setShippingFee(getRestaurant().getShippingFee());
    }

    public void addOrderToItems(){
        getItems().forEach(item->item.setOrder(this));
    }

    public void confirm(){
        setStatus(OrderStatus.CONFIRMED);
        setConfirmationDate(OffsetDateTime.now());
    }

    public void delivery(){
        setStatus(OrderStatus.DELIVERED);
        setConfirmationDate(OffsetDateTime.now());
    }

    public void cancel(){
        setStatus(OrderStatus.CANCELLED);
        setConfirmationDate(OffsetDateTime.now());
    }

    private void setStatus(OrderStatus newStatus){
        if(getStatus().cantChangeTo(newStatus)){
             throw new ModelValidationException("Status of the order %d can't be changed from %s to %s".formatted(getUuid(), getStatus().getDescription(), newStatus.getDescription()));
        }
        this.status = newStatus;
    }

    @PrePersist
    private void generateUuid(){
        setUuid(UUID.randomUUID().toString());
    }

}
