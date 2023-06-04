package com.raysmond.blog.repositories.controllers;

import com.raysmond.blog.models.User;
import com.raysmond.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Controller
@RequestMapping("/UserRepositoryController")
public class UserRepositoryController {

    @Autowired
    UserRepository userRepository;

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        String role = "ADMIN";
        String email = "admin@admin.com";
        Long id = 1L;
        User user = new User();


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


    List<User> findAllByRoleOrderById(String role) {
        return findAllByRoleOrderById(role);
    }

    User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    User findOne(Long id) {
        return userRepository.findOne(id);
    }

    User save(User user) {
        return userRepository.save(user);
    }
}
