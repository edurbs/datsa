package com.github.edurbs.datsa.domain.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements DomainModel {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    OffsetDateTime registrationDate;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    OffsetDateTime lastUpdateDate;

    @ManyToMany
    @JoinTable(name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    Set<Group> groups = new HashSet<>();

    public boolean passwordEqualsTo(String password){
        return this.password.equals(password);
    }

    public boolean passwordNotEqualsTo(String password){
        return !passwordEqualsTo(password);
    }

    public boolean addGroup(Group group) {
        return groups.add(group);
    }

    public boolean removeGroup(Group group){
        return groups.remove(group);
    }

}
