package com.raysmond.blog.microservice0.controllers;

import com.raysmond.blog.microservice0.forms.LikeForm;
import com.raysmond.blog.microservice0.models.Post;
import com.raysmond.blog.microservice0.support.web.ViewHelper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(value = "/sympathy")
public class SympathyController {

    @Autowired
    private AppSetting appSetting;

    @Autowired
    private PostService postService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @Autowired
    private RequestProcessorService requestProcessorService;


    @Data
    public static class SympathyRequestData {
        @NotNull
        private String postId;
    }


    @PostMapping(value = "/like")
    public @ResponseBody
    LikeForm likeIt(@RequestBody SympathyRequestData data, HttpServletRequest request) {

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println("SympathyRequestData");
//        System.out.println(RamUsageEstimator.sizeOf(data));

        Post post = this.postService.findPostByPermalink(data.getPostId());
        this.likeService.likePost(post, this.requestProcessorService.getRealIp(request));
        ViewHelper viewHelper = new ViewHelper(this.appSetting);
        LikeForm result = new LikeForm();
        result.setSympathy(viewHelper.formatNumberByThousands(this.likeService.getTotalLikesByPost(post)));
        return result;
    }

    @PostMapping(value = "/dislike")
    public @ResponseBody
    LikeForm dislikeIt(@RequestBody SympathyRequestData data, HttpServletRequest request) {
        Post post = this.postService.findPostByPermalink(data.getPostId());
        this.likeService.dislikePost(post, this.requestProcessorService.getRealIp(request));
        ViewHelper viewHelper = new ViewHelper(this.appSetting);
        LikeForm result = new LikeForm();
        result.setSympathy(viewHelper.formatNumberByThousands(this.likeService.getTotalLikesByPost(post)));
        return result;
    }


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        SympathyRequestData data = new SympathyRequestData();
        MockHttpServletRequest request = new MockHttpServletRequest();


        if (method.equals("all") || method.equals("likeIt")) {
            likeIt_test(data, request);
        }
        if (method.equals("all") || method.equals("dislikeIt")) {
            dislikeIt_test(data, request);
        }

        return "test";
    }

    public LikeForm likeIt_test(SympathyRequestData data, HttpServletRequest request) {
//        Post post = this.postService.findPostByPermalink(data.getPostId());
        Post post = new Post();
//        this.likeService.likePost(post, this.requestProcessorService.getRealIp(request));
        ViewHelper viewHelper = new ViewHelper(this.appSetting);
        LikeForm result = new LikeForm();
//        result.setSympathy(viewHelper.formatNumberByThousands(this.likeService.getTotalLikesByPost(post)));
        result.setSympathy(viewHelper.formatNumberByThousands(1));
        return result;
    }

    public LikeForm dislikeIt_test(SympathyRequestData data, HttpServletRequest request) {
//        Post post = this.postService.findPostByPermalink(data.getPostId());
        Post post = new Post();
//        this.likeService.dislikePost(post, this.requestProcessorService.getRealIp(request));
        ViewHelper viewHelper = new ViewHelper(this.appSetting);
        LikeForm result = new LikeForm();
//        result.setSympathy(viewHelper.formatNumberByThousands(this.likeService.getTotalLikesByPost(post)));
        result.setSympathy(viewHelper.formatNumberByThousands(1));
        return result;
    }

}
