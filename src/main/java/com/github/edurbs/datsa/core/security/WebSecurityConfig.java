package com.github.edurbs.datsa.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("eduardo")
                .password(passwordEncoder().encode("123"))
                .roles("Admin") // fake (for now)
            .and()
            .withUser("joao")
                .password(passwordEncoder().encode("123"))
                .roles("admin");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic() // basic authentication
            .and()
            .authorizeRequests()
                .antMatchers("/v1/kitchens/**").permitAll() // this url accepts without authentication
                .anyRequest().authenticated() // must be basic authenticated
            .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // not use cookies to identify the user
            .and()
                .csrf().disable(); // to accept put
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
