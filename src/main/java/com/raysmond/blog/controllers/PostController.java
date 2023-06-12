package com.raysmond.blog.controllers;

import com.raysmond.blog.Constants;
import com.raysmond.blog.error.NotFoundException;
import com.raysmond.blog.forms.PostForm;
import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.SeoPostData;
import com.raysmond.blog.models.Tag;
import com.raysmond.blog.models.User;
import com.raysmond.blog.services.*;
import org.apache.lucene.util.RamUsageEstimator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.*;


/**
 * @author Raysmond<i@raysmond.com>
 */
@Controller
@RequestMapping("posts")
public class PostController {

    Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private VisitService visitService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private RequestProcessorService requestProcessorService;


    @RequestMapping(value = "archive", method = GET)
    public String archive(Model model){
        model.addAttribute("posts", postService.getArchivePosts());


//        List<Post> posts = postService.getArchivePosts();
        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println(RamUsageEstimator.sizeOf(posts));
//        model.addAttribute("posts", posts);

        return "posts/archive";
    }

    @RequestMapping(value = "{permalink}", method = GET)
    public String show(@PathVariable String permalink, Model model, HttpServletRequest request){
        Post post = this.postService.findPostByPermalink(permalink);

        logger.debug(String.format("ACCESS %s from IP: %s", permalink, this.requestProcessorService.getRealIp(request)));

        this.visitService.saveVisit(post, this.requestProcessorService.getRealIp(request), this.requestProcessorService.getUserAgent(request));
        post.setVisitsCount(this.visitService.getUniqueVisitsCount(post));
        post.setSympathyCount(this.likeService.getTotalLikesByPost(post));

        SeoPostData seoData = null;
        if (post.getSeoData() == null) {
            seoData = new SeoPostData();
            seoData.setPost(post);
        } else {
            seoData = post.getSeoData();
        }

        model.addAttribute("post", post);
        model.addAttribute("tags", this.postService.getPostTags(post));
        model.addAttribute("seoKeywords", this.postService.getSeoKeywordsAsString(post));
        model.addAttribute("seoDescription", post.getSeoDescription());
        model.addAttribute("seoData", seoData);

//        List<Tag> tags = this.postService.getPostTags(post);
//        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println(RamUsageEstimator.sizeOf(tags));
//        model.addAttribute("tags", tags);


        return "posts/show";
    }

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Model model = new BindingAwareModelMap();
        Principal principal = new UsernamePasswordAuthenticationToken(new User(), null);
        PostForm postForm = new PostForm();
        Errors errors = new BeanPropertyBindingResult(postForm,"postForm",true,256);
        RedirectAttributes ra = new RedirectAttributesModelMap();
        MockHttpServletRequest request = new MockHttpServletRequest();

        if (method.equals("all") || method.equals("archive")) {
            archive_test(model);
        }
        if (method.equals("all") || method.equals("show")) {
            show_test("projects", model, request);
        }

        return "test";
    }

    public String archive_test(Model model){
//        model.addAttribute("posts", postService.getArchivePosts());
        model.addAttribute("posts", new ArrayList<Post>());
        List<Post> posts = new ArrayList<>();
        model.addAttribute("posts", posts);

        return "posts/archive";
    }

    public String show_test(String permalink, Model model, HttpServletRequest request){
//        Post post = this.postService.findPostByPermalink(permalink);
        Post post = new Post();

//        logger.debug(String.format("ACCESS %s from IP: %s", permalink, this.requestProcessorService.getRealIp(request)));
        logger.debug(String.format("ACCESS %s from IP: %s", permalink, "10.60.150.33"));

//        this.visitService.saveVisit(post, this.requestProcessorService.getRealIp(request), this.requestProcessorService.getUserAgent(request));
//        post.setVisitsCount(this.visitService.getUniqueVisitsCount(post));
        post.setVisitsCount(1L);
//        post.setSympathyCount(this.likeService.getTotalLikesByPost(post));
        post.setSympathyCount(1);

        SeoPostData seoData = null;
        if (post.getSeoData() == null) {
            seoData = new SeoPostData();
            seoData.setPost(post);
        } else {
            seoData = post.getSeoData();
        }

        model.addAttribute("post", post);
//        model.addAttribute("tags", this.postService.getPostTags(post));
        model.addAttribute("tags", new ArrayList<Tag>());
//        model.addAttribute("seoKeywords", this.postService.getSeoKeywordsAsString(post));
        model.addAttribute("seoKeywords", "seoKeywords");
        model.addAttribute("seoDescription", post.getSeoDescription());
        model.addAttribute("seoData", seoData);

        return "posts/show";
    }

}
