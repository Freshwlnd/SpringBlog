package com.raysmond.blog.microservice2.services;

import com.domingosuarez.boot.autoconfigure.jade4j.JadeHelper;
import com.raysmond.blog.microservice2.Constants;
import com.raysmond.blog.microservice2.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JadeHelper("userService")
@RequestMapping("/UserService")
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    User defaultUser = null;

    @PostConstruct
    protected void initialize() {
        getSuperUser();
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getSuperUser() {
        List<User> superUsers = this.userRepository.findAllByRoleOrderById(User.ROLE_ADMIN);

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println(RamUsageEstimator.sizeOf(superUsers));

        if (superUsers == null || superUsers.size() == 0) {
            return this.getDefaultSuperUser();
        }
        return superUsers.get(0);
    }

    public User getDefaultSuperUser() {
        User user = userRepository.findByEmail(Constants.DEFAULT_ADMIN_EMAIL);

        if (user == null) {
            user = createUser(new User(Constants.DEFAULT_ADMIN_EMAIL, Constants.DEFAULT_ADMIN_PASSWORD, User.ROLE_ADMIN));
        }

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return createSpringUser(user);
    }

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

        String email = ((User) auth.getPrincipal()).getUsername();

        return userRepository.findByEmail(email);
    }

    public Boolean isCurrentUserAdmin() {
        User user = this.currentUser();
        Boolean isAdmin = user != null ? user.isAdmin() : false;
        return isAdmin;
    }

    public boolean changePassword(User user, String password, String newPassword) {
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

    public void signin(User user) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(user));
    }

    private Authentication authenticate(User user) {
        return new UsernamePasswordAuthenticationToken(createSpringUser(user), null, Collections.singleton(createAuthority(user)));
    }

    private User createSpringUser(User user) {
        return new User(
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

        String email = ((User) auth.getPrincipal()).getUsername();

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
