package com.raysmond.blog.microservice1.repositories.controllers;

import com.raysmond.blog.common.models.User;
import com.raysmond.blog.microservice1.client.UserServiceClient;
import com.raysmond.blog.microservice1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Raysmond<i @ raysmond.com>
 */
@Controller
@RequestMapping("/UserRepositoryController")
public class UserRepositoryController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceClient userService;

    User user = null;
    String role = "ADMIN";
    String email = "admin@admin.com";
    Long id = 1L;

    private void init() {
        user = userService.getSuperUser();
    }

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {

        if (user == null) {
            init();
        }

        switch (method) {
            case "all":
                for (int i = 0; i < 3; i++) {
                    findAllByRoleOrderById(role);
                }
                for (int i = 0; i < 10; i++) {
                    findByEmail(email);
                }
                for (int i = 0; i < 1; i++) {
                    findOne(id);
                }
                for (int i = 0; i < 1; i++) {
                    save(user);
                }
                break;
            case "findAllByRoleOrderById":
                findAllByRoleOrderById(role);
                break;
            case "findByEmail":
                findByEmail(email);
                break;
            case "findOne":
                findOne(id);
                break;
            case "save":
                save(user);
                break;
        }

        return "test";
    }


    @RequestMapping(value = "/findAllByRoleOrderById", method = RequestMethod.GET)
    @ResponseBody
    List<User> findAllByRoleOrderById(@RequestParam("role") String role) {
        return userRepository.findAllByRoleOrderById(role);
    }

    @RequestMapping(value = "/findByEmail", method = RequestMethod.GET)
    @ResponseBody
    User findByEmail(@RequestParam("email") String email) {
        return userRepository.findByEmail(email);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ResponseBody
    User findOne(@RequestParam("id") Long id) {
        return userRepository.findOne(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    User save(@RequestBody User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}
