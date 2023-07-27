package com.raysmond.blog.microservice7.client;

import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.User;
import com.raysmond.blog.common.models.Visit;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@FeignClient(name = "springblog-microservice1")
@RequestMapping("/UserRepositoryController")
public interface UserRepositoryClient {

    @RequestMapping(value = "/findAllByRoleOrderById", method = RequestMethod.GET)
    @ResponseBody
    List<User> findAllByRoleOrderById(@RequestParam("role") String role);

    @RequestMapping(value = "/findByEmail", method = RequestMethod.GET)
    @ResponseBody
    User findByEmail(@RequestParam("email") String email);

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ResponseBody
    User findOne(@RequestParam("id") Long id);

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    User save(@RequestBody User user);

}
