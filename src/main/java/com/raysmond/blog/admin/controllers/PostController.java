package com.raysmond.blog.admin.controllers;

import com.raysmond.blog.forms.PostForm;
import com.raysmond.blog.forms.PostPreviewForm;
import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.User;
import com.raysmond.blog.models.dto.PostPreviewDTO;
import com.raysmond.blog.models.support.*;
import com.raysmond.blog.repositories.UserRepository;
import com.raysmond.blog.services.PostService;
import com.raysmond.blog.services.UserService;
import com.raysmond.blog.support.web.MarkdownService;
import com.raysmond.blog.utils.DTOUtil;
import com.raysmond.blog.utils.PaginatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MarkdownService markdownService;

    // TODO
    private String defaultEmail = "admin@admin.com";

    private static final int PAGE_SIZE = 20;

    @RequestMapping(value = "")
    public String index(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Post> posts = postService.findAllPosts(new PageRequest(page, PAGE_SIZE, Sort.Direction.DESC, "id"));

        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("posts", posts);
        model.addAttribute("pagesList", PaginatorUtil.createPagesList(0, posts.getTotalPages() - 1));

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
    public String newPost(Model model) {
        return this.makeFormPostCreation(model);
    }

    private String makeFormPostEdition(Long postId, Model model) {
        return this.makeFormPostEdition(postId, model, null);
    }

    private String makeFormPostEdition(Long postId, Model model, PostForm postForm) {
        Post post = postService.getPost(postId);

        if (postForm == null) {
            postForm = DTOUtil.map(post, PostForm.class);
        }

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
    public String editPost(@PathVariable Long postId, Model model) {
        return this.makeFormPostEdition(postId, model);
    }

    @RequestMapping(value = "{postId:[0-9]+}/delete", method = {DELETE, POST})
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/admin/posts";
    }

    @RequestMapping(value = "", method = POST)
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
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Model model = new BindingAwareModelMap();
        Principal principal = new UsernamePasswordAuthenticationToken(new User(), null);
        PostForm postForm = new PostForm();
        Errors errors = new BeanPropertyBindingResult(postForm, "postForm", true, 256);
        RedirectAttributes ra = new RedirectAttributesModelMap();
        Long postId = 1L;


        if (method.equals("all") || method.equals("index")) {
            index_test(0, model);
        }
        if (method.equals("all") || method.equals("newPost")) {
            newPost_test(model);
        }
        if (method.equals("all") || method.equals("create")) {
            create_test(principal, postForm, errors, model);
        }
        if (method.equals("all") || method.equals("editPost")) {
            editPost_test(0L, model);
        }
        if (method.equals("all") || method.equals("update")) {
            update_test(0L, postForm, errors, model);
        }

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
