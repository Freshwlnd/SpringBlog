package com.raysmond.blog.microservice1.controllers;

import com.raysmond.blog.microservice1.Constants;
import com.raysmond.blog.microservice1.error.NotFoundException;
import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.microservice1.services.AppSetting;
import com.raysmond.blog.microservice1.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
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

}
