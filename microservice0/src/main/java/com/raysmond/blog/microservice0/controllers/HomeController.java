package com.raysmond.blog.microservice0.controllers;

import com.raysmond.blog.microservice0.Constants;
import com.raysmond.blog.microservice0.error.NotFoundException;
import com.raysmond.blog.microservice0.models.Post;
import com.raysmond.blog.microservice0.services.AppSetting;
import com.raysmond.blog.microservice0.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
//@RequestMapping("home")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private AppSetting appSetting;

    @RequestMapping(value = "", method = GET)
    public String index(@RequestParam(defaultValue = "1") int page, Model model) {
        page = page < 1 ? 0 : page - 1;
        Page<Post> posts = postService.getAllPublishedPostsByPage(page, appSetting.getPageSize());

        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("posts", posts);
        model.addAttribute("page", page + 1);

        return "home/index";
    }

    @RequestMapping(value = "about", method = GET)
    public String about(Model model) {

        Post post = null;
        try {
            post = postService.getPublishedPostByPermalink(Constants.ABOUT_PAGE_PERMALINK);
            // TODO
            postService.createAboutPage();
        } catch (NotFoundException nfe) {
            logger.debug("Get post with permalink " + Constants.ABOUT_PAGE_PERMALINK);
            post = postService.createAboutPage();
        }

        if (post == null) {
            throw new NotFoundException("Post with permalink " + Constants.ABOUT_PAGE_PERMALINK + " is not found");
        }

        model.addAttribute("about", post);
        return "home/about";
    }

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Model model = new BindingAwareModelMap();


        if (method.equals("all") || method.equals("index")) {
            index_test(0, model);
        }
        if (method.equals("all") || method.equals("about")) {
            about_test(model);
        }

        return "test";
    }

    public String index_test(int page, Model model) {
        page = page < 1 ? 0 : page - 1;
//        Page<Post> posts = postService.getAllPublishedPostsByPage(page, appSetting.getPageSize());
        Page<Post> posts = new PageImpl<Post>(new ArrayList<Post>());

        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("posts", posts);
        model.addAttribute("page", page + 1);

        return "home/index";
    }

    public String about_test(Model model) {

        Post post = null;
        try {
//            post = postService.getPublishedPostByPermalink(Constants.ABOUT_PAGE_PERMALINK);
            post = new Post();
        } catch (NotFoundException nfe) {
            logger.debug("Get post with permalink " + Constants.ABOUT_PAGE_PERMALINK);
//            post = postService.createAboutPage();
        }

        if (post == null) {
            throw new NotFoundException("Post with permalink " + Constants.ABOUT_PAGE_PERMALINK + " is not found");
        }

        model.addAttribute("about", post);
        return "home/about";
    }

}
