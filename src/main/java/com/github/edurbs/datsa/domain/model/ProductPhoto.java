package com.github.edurbs.datsa.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductPhoto implements DomainModel {

    @EqualsAndHashCode.Include
    @Id
    @Column(name="product_id")
    Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    Product product;

    String fileName;
    String description;
    String contentType;
    Long size;

    public Long getRestaurantId(){
        if(getProduct()==null || getProduct().getRestaurant() == null) return null;
        return getProduct().getRestaurant().getId();
    }

}
