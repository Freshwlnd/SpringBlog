package com.raysmond.blog.admin.controllers;

import com.raysmond.blog.forms.PostForm;
import com.raysmond.blog.forms.UserForm;
import com.raysmond.blog.models.User;
import com.raysmond.blog.repositories.UserRepository;
import com.raysmond.blog.services.UserService;
import com.raysmond.blog.support.web.MessageHelper;
import com.raysmond.blog.utils.DTOUtil;
import org.apache.lucene.util.RamUsageEstimator;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.validation.Valid;

import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Raysmond<i@raysmond.com>.
 */
@Controller("adminUserController")
@RequestMapping("admin/users")
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @RequestMapping("profile")
    public String profile(Model model){
//        model.addAttribute("user", userService.currentUser());
        User user = userService.currentUser();
        model.addAttribute("user", user);

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println("User: ");
//        System.out.println(RamUsageEstimator.sizeOf(user));
//        System.out.println(RamUsageEstimator.shallowSizeOf(user));
//        System.out.println(ClassLayout.parseInstance(user).toPrintable());

        return "admin/users/profile";
    }

    @RequestMapping(value = "{userId:[0-9]+}", method = POST)
    public String update(@PathVariable Long userId, @Valid UserForm userForm, Errors errors, RedirectAttributes ra){
        User user = userRepository.findOne(userId);
        Assert.notNull(user);

        if (errors.hasErrors()){
            // do something

            return "admin/users/profile";
        }

        if (!userForm.getNewPassword().isEmpty()){

            if (!userService.changePassword(user, userForm.getPassword(), userForm.getNewPassword()))
                MessageHelper.addErrorAttribute(ra, "Change password failed.");
            else
                MessageHelper.addSuccessAttribute(ra, "Change password successfully.");

        }

        return "redirect:profile";
    }

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Model model = new BindingAwareModelMap();
        UserForm userForm = new UserForm();
        Errors errors = new BeanPropertyBindingResult(userForm,"userForm",true,256);
        RedirectAttributes ra = new RedirectAttributesModelMap();


        if (method.equals("all") || method.equals("profile")) {
            profile_test(model);
        }
        if (method.equals("all") || method.equals("update")) {
            update_test(0L, userForm, errors, ra);
        }

        return "test";
    }

    public String profile_test(Model model){
//        model.addAttribute("user", userService.currentUser());
        model.addAttribute("user", new User());

        return "admin/users/profile";
    }

    public String update_test(Long userId, UserForm userForm, Errors errors, RedirectAttributes ra){
//        User user = userRepository.findOne(userId);
        User user = new User();
        Assert.notNull(user);

        if (errors.hasErrors()){
            // do something

            return "admin/users/profile";
        }

        if (!userForm.getNewPassword().isEmpty()){

//            if (!userService.changePassword(user, userForm.getPassword(), userForm.getNewPassword()))
            userForm.getPassword();
            userForm.getNewPassword();
                MessageHelper.addErrorAttribute(ra, "Change password failed.");
//            else
//                MessageHelper.addSuccessAttribute(ra, "Change password successfully.");

        }

        return "redirect:profile";
    }

}
