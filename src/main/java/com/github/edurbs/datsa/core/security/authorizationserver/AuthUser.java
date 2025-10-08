package com.github.edurbs.datsa.core.security.authorizationserver;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.github.edurbs.datsa.domain.model.MyUser;

import lombok.Getter;

@Getter
public class AuthUser extends User {
    private String fullName;
    private String email;
    private Long userId;
    public AuthUser(MyUser myUser, Collection<? extends GrantedAuthority> authorities){
        super(myUser.getEmail(), myUser.getPassword(), authorities);
        this.fullName = myUser.getName();
        this.userId = myUser.getId();
    }
}
