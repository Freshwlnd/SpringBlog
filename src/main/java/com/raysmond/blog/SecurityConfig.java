package com.raysmond.blog;

import com.raysmond.blog.models.User;
import com.raysmond.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // TODO
    private class DefaultLoginFilter extends AbstractAuthenticationProcessingFilter {

        private final AuthenticationManager authenticationManager;

        public DefaultLoginFilter(AuthenticationManager authenticationManager) {
            super(new AntPathRequestMatcher("/authenticate", "POST"));
            this.authenticationManager = authenticationManager;
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
            // 在这里实现自动登录的逻辑
            User user = userService().getSuperUser();
            userService().signin(user);
            return new UsernamePasswordAuthenticationToken(createSpringUser(user), null, Collections.singleton(createAuthority(user)));
        }

        private org.springframework.security.core.userdetails.User createSpringUser(User user) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singleton(createAuthority(user)));
        }

        private GrantedAuthority createAuthority(User user) {
            return new SimpleGrantedAuthority(user.getRole());
        }
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public TokenBasedRememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices services = new TokenBasedRememberMeServices("remember-me-secret", userService());
        services.setTokenValiditySeconds(60*60*24*7); //NB: set timeout here
        return services;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
	}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .eraseCredentials(true)
            .userDetailsService(userService())
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // .httpBasic().disable()
           .authorizeRequests()
               .antMatchers("/admin/**").authenticated()   // one who logged in can access
               //.antMatchers("/authenticate").permitAll()
               .anyRequest().permitAll()   // anyone can access
               .and()
           .formLogin()
               .loginPage("/signin")
               .permitAll()
               .failureUrl("/signin?error=1")
               .loginProcessingUrl("/authenticate")
               .and()
           .logout()
               .logoutUrl("/logout")
               .permitAll()
               .logoutSuccessUrl("/signin?logout")
               .and()
           .rememberMe()
               .rememberMeServices(rememberMeServices())
               .key("remember-me-secret")
               //.tokenValiditySeconds(60*60) //NB: Not here
               .and()
            // TODO
            .addFilterBefore(new DefaultLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)   // 自动登录默认账号
                ;
    }
}
