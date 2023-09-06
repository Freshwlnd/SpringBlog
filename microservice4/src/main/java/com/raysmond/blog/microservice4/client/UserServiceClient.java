package com.raysmond.blog.microservice4.client;

import com.raysmond.blog.common.models.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "springblog-microservice7")
@RequestMapping("/UserService")
public interface UserServiceClient {


    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    User createUser(@RequestBody User user) ;

    @RequestMapping(value = "/getSuperUser", method = RequestMethod.GET)
    @ResponseBody
    User getSuperUser() ;

    @RequestMapping(value = "/getDefaultSuperUser", method = RequestMethod.GET)
    @ResponseBody
    User getDefaultSuperUser() ;

    @RequestMapping(value = "/loadUserByUsername", method = RequestMethod.GET)
    @ResponseBody
    UserDetails loadUserByUsername(@RequestParam("username") String username) throws UsernameNotFoundException ;

    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    @ResponseBody
    User currentUser() ;

    @RequestMapping(value = "/isCurrentUserAdmin", method = RequestMethod.GET)
    @ResponseBody
    Boolean isCurrentUserAdmin();

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    boolean changePassword(@RequestBody User user, @RequestParam("password") String password, @RequestParam("newPassword") String newPassword);

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseBody
    void signin(@RequestBody User user);

}
