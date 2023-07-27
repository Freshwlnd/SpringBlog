package com.raysmond.blog.microservice1;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.httpBasic().disable()
            .authorizeRequests()
                .anyRequest().permitAll()
                .and()
            .csrf()
                .disable()
            .logout()
                .permitAll()
                ;
    }
}
