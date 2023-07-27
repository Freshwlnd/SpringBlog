package com.raysmond.blog.microservice1.client;

import com.raysmond.blog.common.models.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Repository
@FeignClient(name = "springblog-microservice7")
@RequestMapping("/UserService")
public interface UserServiceClient {


    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public User createUser(@RequestBody User user) ;

    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    @ResponseBody
    public User getSuperUser() ;

    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    @ResponseBody
    public User getDefaultSuperUser() ;

    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    @ResponseBody
    public UserDetails loadUserByUsername(@RequestParam("username") String username) throws UsernameNotFoundException ;

    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    @ResponseBody
    public User currentUser() ;

    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public Boolean isCurrentUserAdmin();

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public boolean changePassword(@RequestBody User user, @RequestParam("password") String password, @RequestParam("newPassword") String newPassword);

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public void signin(@RequestBody User user);

}
