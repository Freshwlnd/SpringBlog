package com.raysmond.blog.controllers;

import com.raysmond.blog.error.NotFoundException;
import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.Tag;
import com.raysmond.blog.services.AppSetting;
import com.raysmond.blog.services.PostService;
import com.raysmond.blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Raysmond<i@raysmond.com>.
 */
@Controller
@RequestMapping("tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;

    @Autowired
    private AppSetting appSetting;

    @RequestMapping(value = "", method = GET)
    public String index(Model model){
        model.addAttribute("tags", postService.countPostsByTags());
        return "tags/index";
    }

    @RequestMapping(value = "{tagName}", method = GET)
    public String showTag(@PathVariable String tagName, @RequestParam(defaultValue = "1") int page, Model model) {
        Tag tag = tagService.getTag(tagName);

        if (tag == null) {
            throw new NotFoundException("Tag " + tagName + " is not found.");
        }

        page = page < 1 ? 0 : page - 1;
        Page<Post> posts = postService.findPostsByTag(tagName, page, appSetting.getPageSize());

        model.addAttribute("tag", tag);
        model.addAttribute("posts", posts);
        model.addAttribute("page", page + 1);
        model.addAttribute("totalPages", posts.getTotalPages());

        return "tags/show";
    }

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Model model = new BindingAwareModelMap();
        String tagName = "tag11";
        int page = 1;


        if (method.equals("all") || method.equals("index")) {
            index_test(model);
        }
        if (method.equals("all") || method.equals("showTag")) {
            showTag_test(tagName, page, model);
        }

        return "test";
    }

    public String index_test(Model model){
//        model.addAttribute("tags", postService.countPostsByTags());
        model.addAttribute("tags", new ArrayList<>());
        return "tags/index";
    }

    public String showTag_test(String tagName, @RequestParam(defaultValue = "1") int page, Model model) {
//        Tag tag = tagService.getTag(tagName);
        Tag tag = new Tag();

        if (tag == null) {
            throw new NotFoundException("Tag " + tagName + " is not found.");
        }

        page = page < 1 ? 0 : page - 1;
//        Page<Post> posts = postService.findPostsByTag(tagName, page, appSetting.getPageSize());
        Page<Post> posts = new PageImpl<Post>(new ArrayList<Post>());

        model.addAttribute("tag", tag);
        model.addAttribute("posts", posts);
        model.addAttribute("page", page + 1);
        model.addAttribute("totalPages", posts.getTotalPages());

        return "tags/show";
    }

}
