package com.raysmond.blog.microservice1.admin.controllers;

import com.raysmond.blog.common.models.User;
import com.raysmond.blog.microservice1.client.MarkdownServiceClient;
import com.raysmond.blog.microservice1.client.PaginatorUtilClient;
import com.raysmond.blog.common.forms.PostForm;
import com.raysmond.blog.common.forms.PostPreviewForm;
import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.dto.PostPreviewDTO;
import com.raysmond.blog.common.models.support.*;
import com.raysmond.blog.microservice1.client.PostServiceClient;
import com.raysmond.blog.microservice1.repositories.UserRepository;
import com.raysmond.blog.microservice1.utils.DTOUtil;
import com.raysmond.blog.microservice1.utils.PaginatorUtil;
import org.apache.lucene.util.RamUsageEstimator;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
// import com.raysmond.blog.common.models.PageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Raysmond<i @ raysmond.com>
 */
@Controller("adminPostController")
@RequestMapping("admin/posts")
public class PostController {

    @Autowired
    private PostServiceClient postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MarkdownServiceClient markdownService;

    // TODO
    private String defaultEmail = "admin@admin.com";

    private static final int PAGE_SIZE = 20;


    // TODO

    @RequestMapping(value = "testCreate", method = RequestMethod.GET)
    @ResponseBody
    public String testCreate() {
        Model model = new BindingAwareModelMap();
        Principal principal = new UsernamePasswordAuthenticationToken(new User(), null);
        PostForm postForm = new PostForm();
        postForm.init();
        Errors errors = new BeanPropertyBindingResult(postForm, "postForm", true, 256);

        create(principal, postForm, errors, model);

        return "test";
    }

    @RequestMapping(value = "testDeletePost", method = RequestMethod.GET)
    @ResponseBody
    public String testDeletePost() {
        Long postId = 1L;

        deletePost(postId);

        return "test";
    }

    @RequestMapping(value = "testEditPost", method = RequestMethod.GET)
    @ResponseBody
    public String testEditPost() {
        Model model = new BindingAwareModelMap();
        Long postId = 1L;

        editPost(postId, model);

        return "test";
    }

    @RequestMapping(value = "testIndex", method = RequestMethod.GET)
    @ResponseBody
    public String testIndex() {
        Model model = new BindingAwareModelMap();

        index(0, model);

        return "test";
    }

    @RequestMapping(value = "testNewPost", method = RequestMethod.GET)
    @ResponseBody
    public String testNewPost() {
        Model model = new BindingAwareModelMap();

        newPost(model);

        return "test";
    }

    @RequestMapping(value = "testUpdate", method = RequestMethod.GET)
    @ResponseBody
    public String testUpdate() {
        Model model = new BindingAwareModelMap();
        PostForm postForm = new PostForm();
        postForm.init();
        Errors errors = new BeanPropertyBindingResult(postForm, "postForm", true, 256);
        Long postId = 15L;

        update(postId, postForm, errors, model);

        return "test";
    }

    ////////////////////////////////////


    @RequestMapping(value = "")
    @ResponseBody
    public String index(@RequestParam(defaultValue = "0") int page, Model model) {
//        Page<Post> posts = postService.findAllPosts(new PageRequest(page, PAGE_SIZE, Sort.Direction.DESC, "id"));
        PageRequest pgReq = new PageRequest(page, PAGE_SIZE, Sort.Direction.DESC, "id");
        Page<Post> posts = postService.findAllPosts(pgReq);

        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("posts", posts);
        model.addAttribute("pagesList", PaginatorUtil.createPagesList(0, posts.getTotalPages() - 1));


//        List<Integer> pageList = PaginatorUtil.createPagesList(0, posts.getTotalPages() - 1);
        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println(RamUsageEstimator.sizeOf(pageList));
//        System.out.println("Page<com.raysmond.blog.microservice0.models.Post>");
//        System.out.println(RamUsageEstimator.sizeOf(posts));
//        System.out.println(RamUsageEstimator.shallowSizeOf(posts));
//        System.out.println(ClassLayout.parseInstance(posts).toPrintable());
//        System.out.println("PageRequest");
//        System.out.println(RamUsageEstimator.sizeOf(pgReq));
//        model.addAttribute("pagesList", pageList);

        return "admin/posts/index";
    }

    private String makeFormPostCreation(Model model) {
        PostForm postForm = DTOUtil.map(new Post(), PostForm.class);
        return this.makeFormPostCreation(model, postForm);
    }

    private String makeFormPostCreation(Model model, PostForm postForm) {

        postForm.init();

        model.addAttribute("postForm", postForm);
        model.addAttribute("postFormats", PostFormat.values());
        model.addAttribute("postStatus", PostStatus.values());
        model.addAttribute("seoOgLocales", OgLocale.values());
        model.addAttribute("seoOgTypes", OgType.values());

        return "admin/posts/new";
    }

    @RequestMapping(value = "new")
    @ResponseBody
    public String newPost(Model model) {
        return this.makeFormPostCreation(model);
    }

    private String makeFormPostEdition(Long postId, Model model) {
        return this.makeFormPostEdition(postId, model, null);
    }

    private String makeFormPostEdition(Long postId, Model model, PostForm postForm) {
        Post post = postService.getPost(postId);

        if(post == null){
            post = new Post();
            post.init();
        }
        if (postForm == null) {
            postForm = DTOUtil.map(post, PostForm.class);
        }

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println("Set<com.raysmond.blog.microservice0.models.Tag>");
//        System.out.println(RamUsageEstimator.sizeOf(post.getTags()));
//        System.out.println(RamUsageEstimator.shallowSizeOf(post.getTags()));
//        System.out.println(ClassLayout.parseInstance(post.getTags()).toPrintable());
//        System.out.println("com.raysmond.blog.microservice0.forms.PostForm");
//        System.out.println(RamUsageEstimator.sizeOf(postForm));
//        System.out.println("com.raysmond.blog.microservice0.models.Post");
//        System.out.println(RamUsageEstimator.sizeOf(post));
//        System.out.println(RamUsageEstimator.shallowSizeOf(post));
//        System.out.println(ClassLayout.parseInstance(post).toPrintable());


        postForm.init();
        DTOUtil.mapTo(post, postForm);
        postForm.initFromPost(post, postService.getTagNames(post.getTags()));

        model.addAttribute("post", post);
        model.addAttribute("postForm", postForm);
        model.addAttribute("postFormats", PostFormat.values());
        model.addAttribute("postStatus", PostStatus.values());
        model.addAttribute("seoOgLocales", OgLocale.values());
        model.addAttribute("seoOgTypes", OgType.values());

        return "admin/posts/edit";
    }

    @RequestMapping(value = "{postId:[0-9]+}/edit")
    @ResponseBody
    public String editPost(@PathVariable Long postId, Model model) {
        return this.makeFormPostEdition(postId, model);
    }

    @RequestMapping(value = "{postId:[0-9]+}/delete", method = {DELETE, POST})
    @ResponseBody
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/admin/posts";
    }

    @RequestMapping(value = "", method = POST)
    @ResponseBody
    public String create(Principal principal, @Valid PostForm postForm, Errors errors, Model model) {
        // TODO
        makeFormPostCreation(new BindingAwareModelMap(), new PostForm());
        if (errors.hasErrors()) {
            Map<String, WebError> webErrors = new HashMap<>();
            errors.getAllErrors().forEach(e -> {
                String field = ((FieldError) e).getField();
                webErrors.put(field, new WebError(field, e.getDefaultMessage()));
            });
            model.addAttribute("errors", webErrors);
            return this.makeFormPostCreation(model, postForm);
        } else {
            Post post = DTOUtil.map(postForm, Post.class);
            // TODO
//            post.setUser(userRepository.findByEmail(principal.getName()));
            post.setUser(userRepository.findByEmail(defaultEmail));
            post.setTags(postService.parseTagNames(postForm.getPostTags()));
            postForm.fillOgFieldsInPost(post);

            postService.createPost(post);

            return "redirect:/admin/posts";
        }
    }

    @RequestMapping(value = "{postId:[0-9]+}", method = {PUT, POST})
    @ResponseBody
    public String update(@PathVariable Long postId, @Valid PostForm postForm, Errors errors, Model model) {
        // TODO
        makeFormPostEdition(postId, new BindingAwareModelMap(), new PostForm());
        if (errors.hasErrors()) {
            Map<String, WebError> webErrors = new HashMap<>();
            errors.getAllErrors().forEach(e -> {
                String field = ((FieldError) e).getField();
                webErrors.put(field, new WebError(field, e.getDefaultMessage()));
            });
            model.addAttribute("errors", webErrors);
            return this.makeFormPostEdition(postId, model, postForm);
        } else {
            Post post = postService.getPost(postId);
            if(post == null) {
                post = new Post();
                post.init();
            }
            DTOUtil.mapTo(postForm, post);
            post.setTags(postService.parseTagNames(postForm.getPostTags()));
            postForm.fillOgFieldsInPost(post);

            postService.updatePost(post);

            return "redirect:/admin/posts";
        }
    }

    @RequestMapping(value = "/preview", method = {PUT, POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    PostPreviewDTO preview(@RequestBody @Valid PostPreviewForm postPreviewForm, Errors errors, Model model) throws Exception {

        if (errors.hasErrors()) {
            throw new Exception("Error occurred!");
        }

        return new PostPreviewDTO(markdownService.renderToHtml(postPreviewForm.getContent()));
    }

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    @ResponseBody
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Model model = new BindingAwareModelMap();
        Principal principal = new UsernamePasswordAuthenticationToken(new User(), null);
        PostForm postForm = new PostForm();
        Errors errors = new BeanPropertyBindingResult(postForm, "postForm", true, 256);
        RedirectAttributes ra = new RedirectAttributesModelMap();
        Long postId = 1L;


        switch (method) {
            case "all":
                for (int i = 0; i < 1; i++) {
                    create_test(principal, postForm, errors, model);
                }
                for (int i = 0; i < 1; i++) {
                    deletePost_test(postId);
                }
                for (int i = 0; i < 1; i++) {
                    editPost_test(postId, model);
                }
                for (int i = 0; i < 1; i++) {
                    index_test(0, model);
                }
                for (int i = 0; i < 2; i++) {
                    makeFormPostCreation_test(model);
                }
                for (int i = 0; i < 2; i++) {
                    makeFormPostEdition_test(postId, model);
                }
                for (int i = 0; i < 1; i++) {
                    newPost_test(model);
                }
                for (int i = 0; i < 1; i++) {
                    update_test(postId, postForm, errors, model);
                }
                break;
            case "create":
                create_test(principal, postForm, errors, model);
                break;
            case "deletePost":
                deletePost_test(postId);
                break;
            case "editPost":
                editPost_test(postId, model);
                break;
            case "index":
                index_test(0, model);
                break;
            case "makeFormPostCreation":
                makeFormPostCreation_test(model);
                break;
            case "makeFormPostEdition":
                makeFormPostEdition_test(postId, model);
                break;
            case "newPost":
                newPost_test(model);
                break;
            case "update":
                update_test(postId, postForm, errors, model);
                break;
        }


        return "test";
    }

    public String index_test(int page, Model model) {
//        Page<Post> posts = postService.findAllPosts(new PageRequest(page, PAGE_SIZE, Sort.Direction.DESC, "id"));

        Page<Post> posts = new PageImpl<Post>(new ArrayList<Post>());

        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("posts", posts);
//        model.addAttribute("pagesList", PaginatorUtil.createPagesList(0, posts.getTotalPages()-1));
        model.addAttribute("pagesList", new ArrayList<Integer>());

        return "admin/posts/index";
    }

    public String newPost_test(Model model) {
//        return this.makeFormPostCreation_test(model);
        return "admin/posts/new";
    }

    private String makeFormPostCreation_test(Model model) {
//        PostForm postForm = DTOUtil.map(new Post(), PostForm.class);
        PostForm postForm = new PostForm();
        return this.makeFormPostCreation_test(model, postForm);
    }

    private String makeFormPostCreation_test(Model model, PostForm postForm) {

        postForm.init();

        model.addAttribute("postForm", postForm);
        model.addAttribute("postFormats", PostFormat.values());
        model.addAttribute("postStatus", PostStatus.values());
        model.addAttribute("seoOgLocales", OgLocale.values());
        model.addAttribute("seoOgTypes", OgType.values());

        return "admin/posts/new";
    }

    public String create_test(Principal principal, PostForm postForm, Errors errors, Model model) {
        this.makeFormPostCreation(model, postForm);
        if (errors.hasErrors()) {
            Map<String, WebError> webErrors = new HashMap<>();
            errors.getAllErrors().forEach(e -> {
                String field = ((FieldError) e).getField();
                webErrors.put(field, new WebError(field, e.getDefaultMessage()));
            });
            model.addAttribute("errors", webErrors);
//            return this.makeFormPostCreation(model, postForm);
            return "redirect:/admin/posts";
        } else {
//            Post post = DTOUtil.map(postForm, Post.class);
            Post post = new Post();
//            post.setUser(userRepository.findByEmail(principal.getName()));
            post.setUser(new User());
//            post.setTags(postService.parseTagNames(postForm.getPostTags()));
            post.setTags(new HashSet<>());
            postForm.fillOgFieldsInPost(post);

//            postService.createPost(post);

            return "redirect:/admin/posts";
        }
    }

    public String editPost_test(Long postId, Model model) {
//        return this.makeFormPostEdition_test(postId, model);
        return "redirect:/admin/posts";
    }

    public String update_test(Long postId, PostForm postForm, Errors errors, Model model) {
//        this.makeFormPostEdition_test(postId, model, postForm);
        if (errors.hasErrors()) {
            Map<String, WebError> webErrors = new HashMap<>();
            errors.getAllErrors().forEach(e -> {
                String field = ((FieldError) e).getField();
                webErrors.put(field, new WebError(field, e.getDefaultMessage()));
            });
            model.addAttribute("errors", webErrors);
//            return this.makeFormPostEdition(postId, model, postForm);
            return "redirect:/admin/posts";
        } else {
//            Post post = postService.getPost(postId);
            Post post = new Post();
//            DTOUtil.mapTo(postForm, post);
//            post.setTags(postService.parseTagNames(postForm.getPostTags()));
            post.setTags(new HashSet<>());
            postForm.fillOgFieldsInPost(post);

//            postService.updatePost(post);

            return "redirect:/admin/posts";
        }
    }

    private String makeFormPostEdition_test(Long postId, Model model) {
        return this.makeFormPostEdition_test(postId, model, null);
    }

    private String makeFormPostEdition_test(Long postId, Model model, PostForm postForm) {
//        Post post = postService.getPost(postId);
        Post post = new Post();

        if (postForm == null) {
//            postForm = DTOUtil.map(post, PostForm.class);
            postForm = new PostForm();
        }

        postForm.init();
//        DTOUtil.mapTo(post, postForm);
//        postForm.initFromPost(post, postService.getTagNames(post.getTags()));
        postForm.initFromPost(post, "tags");

        model.addAttribute("post", post);
        model.addAttribute("postForm", postForm);
        model.addAttribute("postFormats", PostFormat.values());
        model.addAttribute("postStatus", PostStatus.values());
        model.addAttribute("seoOgLocales", OgLocale.values());
        model.addAttribute("seoOgTypes", OgType.values());

        return "admin/posts/edit";
    }

    public String deletePost_test(Long postId) {
//        postService.deletePost(postId);
        return "redirect:/admin/posts";
    }

}
