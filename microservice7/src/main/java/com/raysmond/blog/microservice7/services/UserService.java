package com.raysmond.blog.microservice7.services;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
//import javax.inject.Inject;

//import com.domingosuarez.boot.autoconfigure.jade4j.JadeHelper;
import com.raysmond.blog.microservice7.Constants;
import com.raysmond.blog.common.models.User;
import com.raysmond.blog.microservice7.client.UserRepositoryClient;
import org.apache.lucene.util.RamUsageEstimator;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.bind.annotation.*;

//@JadeHelper("userService")
@Service
@RequestMapping("/UserService")
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepositoryClient userRepository;

//    @Inject
    private PasswordEncoder passwordEncoder = new StandardPasswordEncoder();

    User defaultUser = null;

    @PostConstruct
    protected void initialize() {
        getSuperUser();
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public User createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @RequestMapping(value = "/getSuperUser", method = RequestMethod.GET)
    @ResponseBody
    public User getSuperUser() {
        List<User> superUsers = this.userRepository.findAllByRoleOrderById(User.ROLE_ADMIN);

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println(RamUsageEstimator.sizeOf(superUsers));

        if (superUsers == null || superUsers.size() == 0) {
            return this.getDefaultSuperUser();
        }
        return superUsers.get(0);
    }

    @RequestMapping(value = "/getDefaultSuperUser", method = RequestMethod.GET)
    @ResponseBody
    public User getDefaultSuperUser() {
        User user = userRepository.findByEmail(Constants.DEFAULT_ADMIN_EMAIL);

        if (user == null) {
            user = createUser(new User(Constants.DEFAULT_ADMIN_EMAIL, Constants.DEFAULT_ADMIN_PASSWORD, User.ROLE_ADMIN));
        }

        return user;
    }

    @RequestMapping(value = "/loadUserByUsername", method = RequestMethod.GET)
    @ResponseBody
    @Override
    public UserDetails loadUserByUsername(@RequestParam("username") String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return createSpringUser(user);
    }

    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    @ResponseBody
    public User currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
//            return null;
            // TODO
            if (defaultUser == null) {
                defaultUser = getSuperUser();
            }
            auth = authenticate(defaultUser);
        }

        String email = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();

        return userRepository.findByEmail(email);
    }

    @RequestMapping(value = "/isCurrentUserAdmin", method = RequestMethod.GET)
    @ResponseBody
    public Boolean isCurrentUserAdmin() {
        User user = this.currentUser();
        Boolean isAdmin = user != null ? user.isAdmin() : false;
        return isAdmin;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public boolean changePassword(@RequestBody User user, @RequestParam("password") String password, @RequestParam("newPassword") String newPassword) {
        if (password == null || newPassword == null || password.isEmpty() || newPassword.isEmpty())
            return false;

        logger.info("" + passwordEncoder.matches(password, user.getPassword()));
        boolean match = passwordEncoder.matches(password, user.getPassword());
        if (!match)
            return false;

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        logger.info("User @" + user.getEmail() + " changed password.");

        return true;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseBody
    public void signin(@RequestBody User user) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(user));
    }

    private Authentication authenticate(User user) {
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


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        User user = new User();
        String password = "password";
        String newPassword = "password";

        switch (method) {
            case "all":
                for (int i = 0; i < 1; i++) {
                    changePassword_test(user, password, newPassword);
                }
                for (int i = 0; i < 9; i++) {
                    currentUser_test();
                }
                for (int i = 0; i < 3; i++) {
                    getSuperUser_test();
                }
                for (int i = 0; i < 3; i++) {
                    isCurrentUserAdmin_test();
                }
                break;
            case "changePassword":
                changePassword_test(user, password, newPassword);
                break;
            case "currentUser":
                currentUser_test();
                break;
            case "getSuperUser":
                getSuperUser_test();
                break;
            case "isCurrentUserAdmin":
                isCurrentUserAdmin_test();
                break;
        }

        return "test";
    }

    boolean changePassword_test(User user, String password, String newPassword) {
        if (password == null || newPassword == null || password.isEmpty() || newPassword.isEmpty())
            return false;

        logger.info("" + passwordEncoder.matches(password, user.getPassword()));
        boolean match = passwordEncoder.matches(password, user.getPassword());
        if (!match)
            return false;

        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);

        logger.info("User @" + user.getEmail() + " changed password.");

        return true;
    }

    User currentUser_test() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }

        String email = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();

//        return userRepository.findByEmail(email);
        return new User();
    }

    User getSuperUser_test() {
//        List<User> superUsers = this.userRepository.findAllByRoleOrderById(User.ROLE_ADMIN);
        List<User> superUsers = new ArrayList<>();
        if (superUsers == null || superUsers.size() == 0) {
            return new User();
//            return this.getDefaultSuperUser();
        }
        return superUsers.get(0);
    }

    Boolean isCurrentUserAdmin_test() {
//        User user = this.currentUser();
        User user = new User();
        Boolean isAdmin = user != null ? user.isAdmin() : false;
        return isAdmin;
    }

}
