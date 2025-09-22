package com.github.edurbs.datsa.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
}
