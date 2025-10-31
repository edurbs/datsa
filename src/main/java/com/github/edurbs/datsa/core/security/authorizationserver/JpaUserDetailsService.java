package com.github.edurbs.datsa.core.security.authorizationserver;

import com.github.edurbs.datsa.domain.model.MyUser;
import com.github.edurbs.datsa.domain.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new User(myUser.getEmail(), myUser.getPassword(), getAuthorities(myUser));
    }

    private Collection<GrantedAuthority> getAuthorities(MyUser myUser){
        return myUser.getGroups().stream()
            .flatMap(group -> group.getPermissions().stream())
            .map(permission -> new SimpleGrantedAuthority(permission.getName().toUpperCase()))
            .collect(Collectors.toSet());
    }

}
