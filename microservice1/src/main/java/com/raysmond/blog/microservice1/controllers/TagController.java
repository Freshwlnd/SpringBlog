package com.raysmond.blog.microservice1.controllers;

import com.raysmond.blog.microservice1.client.AppSettingClient;
import com.raysmond.blog.microservice1.client.PostServiceClient;
import com.raysmond.blog.microservice1.client.TagServiceClient;
import com.raysmond.blog.microservice1.error.NotFoundException;
import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Raysmond<i @ raysmond.com>.
 */
@Controller
@RequestMapping("tags")
public class TagController {
    @Autowired
    private TagServiceClient tagService;

    @Autowired
    private PostServiceClient postService;

    @Autowired
    private AppSettingClient appSetting;

    // TODO

    @RequestMapping(value = "testIndex", method = RequestMethod.GET)
    @ResponseBody
    public String testIndex() {
        Model model = new BindingAwareModelMap();

        index(model);

        return "test";
    }

    @RequestMapping(value = "testShowTag", method = RequestMethod.GET)
    @ResponseBody
    public String testShowTag() {
        Model model = new BindingAwareModelMap();
        String tagName = "tag11";
        int page = 1;

        showTag(tagName, page, model);

        return "test";
    }

    ////////////////////////////////////

    @RequestMapping(value = "", method = GET)
    public String index(Model model) {

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println(RamUsageEstimator.sizeOf(model));

        model.addAttribute("tags", postService.countPostsByTags());
        return "tags/index";
    }

    @RequestMapping(value = "{tagName}", method = GET)
    public String showTag(@PathVariable String tagName, @RequestParam(defaultValue = "1") int page, Model model) {
        Tag tag = tagService.getTag(tagName);

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println("Tag: ");
//        System.out.println(RamUsageEstimator.sizeOf(tag));
//        System.out.println(RamUsageEstimator.shallowSizeOf(tag));
//        System.out.println(ClassLayout.parseInstance(tag).toPrintable());

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

    public String index_test(Model model) {
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
