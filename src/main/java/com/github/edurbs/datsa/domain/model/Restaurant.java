package com.github.edurbs.datsa.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.github.edurbs.datsa.domain.exception.ProductNotFoundException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Restaurant implements DomainModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal shippingFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kitchen_id", nullable = false)
    private Kitchen kitchen;

    @Embedded
    private Address address;

    @Column
    private boolean active = true;

    @Column
    private boolean open = true;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime registrationDate;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime lastUpdateDate;

    @ManyToMany
    @JoinTable(name = "restaurant_payment_method",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    private Set<Product> products = new HashSet<>();

    @ManyToMany
    @JoinTable(name="restaurant_user",
        joinColumns = @JoinColumn(name="restaurant_id"),
        inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private Set<MyUser> users = new HashSet<>();

    public void activate() {
        setActive(true);
    }

    public void inactivate() {
        setActive(false);
    }

    public boolean isInactive() {
        return !active;
    }

    public boolean removePaymentMethod(PaymentMethod paymentMethod){
        return getPaymentMethods().remove(paymentMethod);
    }

    public boolean addPaymentMethod(PaymentMethod paymentMethod){
        return getPaymentMethods().add(paymentMethod);
    }

    public boolean acceptPaymentMethod(PaymentMethod paymentMethod){
        return getPaymentMethods().contains(paymentMethod);
    }

    public boolean notAcceptPaymentMethod(PaymentMethod paymentMethod){
        return !acceptPaymentMethod(paymentMethod);
    }

    public Product getProduct(Long id){
        return getProducts().stream()
            .filter(product -> product.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public void open() {
        open=true;
    }

    public void close(){
        open=false;
    }

    public boolean isClosed() {
        return !isOpen();
    }

    public boolean canBeClosed(){
        return isOpen();
    }

    public boolean canBeOpened(){
        return isClosed() && isActive();
    }

    public boolean canBeActivated(){
        return isInactive();
    }

    public boolean canBeInactivated(){
        return isActive();
    }

    public boolean addUser(MyUser user){
        return getUsers().add(user);
    }

    public boolean removeUser(MyUser user){
        return getUsers().remove(user);
    }


}
