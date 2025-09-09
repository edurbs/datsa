package com.github.edurbs.datsa.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

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

}
