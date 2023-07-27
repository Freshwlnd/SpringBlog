package com.raysmond.blog.microservice1.controllers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author Raysmond<i @ raysmond.com>
 */
@Controller
public class UserController {

    public static class Credentials {
        @Getter
        @Setter
        private String username;

        @Getter
        @Setter
        private String password;
    }

//    @Autowired
//    @Qualifier("authenticationManager")
//    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "signin", method = RequestMethod.GET)
    public String signin(Principal principal, RedirectAttributes ra) {
        return principal == null ? "users/signin" : "redirect:/";
    }

    @RequestMapping(value = "authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String authenticate(Credentials credentials, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());

//        try {
//            Authentication auth = authenticationManager.authenticate(token);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//            if (auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
//                request.getSession();
                return "redirect:/admin";
//            } else {
//                return "users/signin?error=1";
//            }
//        } catch (BadCredentialsException e) {
//            return "users/signin?error=1";
//        }
    }

//    // TODO
//    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
//    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
//        Principal principal = new UsernamePasswordAuthenticationToken(new com.raysmond.blog.models.User(), null);
//        RedirectAttributes ra = new RedirectAttributesModelMap();
//        Credentials credentials = new Credentials();
//        MockHttpServletRequest request = new MockHttpServletRequest();
//
//
//        if (method.equals("all") || method.equals("signin")) {
//            signin_test(principal, ra);
//        }
//        if (method.equals("all") || method.equals("authenticate")) {
//            authenticate_test(credentials, request);
//        }
//
//        return "test";
//    }
//
//    public String signin_test(Principal principal, RedirectAttributes ra) {
//        return principal == null ? "users/signin" : "redirect:/";
//    }
//
//    public String authenticate_test(Credentials credentials, HttpServletRequest request) {
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
//
//        try {
//            Authentication auth = authenticationManager.authenticate(token);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//            if (auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
//                request.getSession();
//                return "redirect:/admin";
//            } else {
//                return "users/signin?error=1";
//            }
//        } catch (BadCredentialsException e) {
//            return "users/signin?error=1";
//        }
//    }

}