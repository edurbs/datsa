package com.github.edurbs.datsa.domain.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItem implements DomainModel {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    private String note;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Order order;

    public void calcTotalAmount(){
        BigDecimal thisUnitPrice = this.getUnitPrice();
        Integer thisQuantity = this.getQuantity();
        if(thisUnitPrice==null){
            thisUnitPrice = BigDecimal.ZERO;
        }
        if(thisQuantity==null){
            thisQuantity=0;
        }
        this.setTotalPrice(thisUnitPrice.multiply(new BigDecimal(thisQuantity)));
    }

}
