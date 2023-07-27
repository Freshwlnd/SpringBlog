package com.raysmond.blog.microservice1.repositories.controllers;

import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.PostParams;
import com.raysmond.blog.common.models.support.PostStatus;
import com.raysmond.blog.common.models.support.PostType;
import com.raysmond.blog.microservice1.client.PostServiceClient;
import com.raysmond.blog.microservice1.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Raysmond<i @ raysmond.com>
 */
@Controller
@RequestMapping("/PostRepositoryController")
public class PostRepositoryController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostServiceClient postService;

    Post post = null;
    Post deletePost = null;
    PageRequest pageRequest = null;
    PostStatus postStatus = PostStatus.PUBLISHED;
    PostType postType = PostType.POST;
    Boolean deleted = false;
    Long postId = 0L;
    String permalink = "permalink";
    String tag = "tag";

    private void init() {
        post = this.postService.getPost(1L);
        pageRequest = new PageRequest(0, Integer.MAX_VALUE, Sort.Direction.DESC, "createdAt");
        deletePost = new Post();
        deletePost.setId(0L);
        deletePost.setPermalink(permalink);
    }

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    @ResponseBody
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {

        if (post == null) {
            init();
        }

        switch (method) {
            case "all":
                for (int i = 0; i < 1; i++) {
                    countPostsByTags(postStatus);
                }
                for (int i = 0; i < 1; i++) {
                    delete(deletePost);
                }
                for (int i = 0; i < 2; i++) {
                    findAll();
                }
                for (int i = 0; i < 3; i++) {
                    findAllByPostTypeAndPostStatusAndDeleted(postType, postStatus, pageRequest, deleted);
                }
                for (int i = 0; i < 3; i++) {
                    findByIdAndPostStatusAndDeleted(postId, postStatus, deleted);
                }
                for (int i = 0; i < 4; i++) {
                    findByPermalinkAndPostStatusAndDeleted(permalink, postStatus, deleted);
                }
                for (int i = 0; i < 1; i++) {
                    findByTag(tag, pageRequest);
                }
                for (int i = 0; i < 9; i++) {
                    findOne(postId);
                }
                for (int i = 0; i < 5; i++) {
                    save(post);
                }
                break;
            case "countPostsByTags":
                countPostsByTags(postStatus);
                break;
            case "delete":
                delete(deletePost);
                break;
            case "findAll":
                findAll();
                break;
            case "findAllByPostTypeAndPostStatusAndDeleted":
                findAllByPostTypeAndPostStatusAndDeleted(postType, postStatus, pageRequest, deleted);
                break;
            case "findByIdAndPostStatusAndDeleted":
                findByIdAndPostStatusAndDeleted(postId, postStatus, deleted);
                break;
            case "findByPermalinkAndPostStatusAndDeleted":
                findByPermalinkAndPostStatusAndDeleted(permalink, postStatus, deleted);
                break;
            case "findByTag":
                findByTag(tag, pageRequest);
                break;
            case "findOne":
                findOne(postId);
                break;
            case "save":
                save(post);
                break;
        }

        return "test";
    }

    // Page<Post> findAllByPostTypeAndDeleted(PostType postType, Pageable pageRequest, Boolean deleted);



    @RequestMapping(value = "/findAllByPostTypeAndPostStatusAndDeleted_List", method = RequestMethod.POST)
    @ResponseBody
    List<Post> findAllByPostTypeAndPostStatusAndDeletedList(@RequestBody PostParams postPrams) {
        PostType postType = postPrams.getPostType();
        PostStatus postStatus = postPrams.getPostStatus();
        Boolean deleted = postPrams.getDeleted();
        return postRepository.findAllByPostTypeAndPostStatusAndDeleted(postType, postStatus, deleted);
    }
    
    List<Post> findAllByPostTypeAndPostStatusAndDeleted(PostType postType, PostStatus postStatus, Boolean deleted) {
        return postRepository.findAllByPostTypeAndPostStatusAndDeleted(postType, postStatus, deleted);
    }

    @RequestMapping(value = "/findAllByDeleted", method = RequestMethod.POST)
    @ResponseBody
    Page<Post> findAllByDeleted(@RequestBody Pageable pageRequest, @RequestParam("deleted") Boolean deleted) {
        return postRepository.findAllByDeleted(pageRequest, deleted);
    }

    @RequestMapping(value = "/countPostsByTags", method = RequestMethod.POST)
    @ResponseBody
    List<Object[]> countPostsByTags(@RequestBody PostStatus status) {
        return postRepository.countPostsByTags(status);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    void delete(@RequestBody Post post) {
        postRepository.delete(post);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    List<Post> findAll() {
        List<Post> post = postRepository.findAll();
        return postRepository.findAll();
    }

    @RequestMapping(value = "/findAllByPostTypeAndPostStatusAndDeleted", method = RequestMethod.POST)
    @ResponseBody
    Page<Post> findAllByPostTypeAndPostStatusAndDeleted(@RequestBody PostParams postPrams) {
        PostType postType = postPrams.getPostType();
        PostStatus postStatus = postPrams.getPostStatus();
        Pageable pageRequest = postPrams.getPageRequest();
        Boolean deleted = postPrams.getDeleted();
        return postRepository.findAllByPostTypeAndPostStatusAndDeleted(postType, postStatus, pageRequest, deleted);
    }

    Page<Post> findAllByPostTypeAndPostStatusAndDeleted(PostType postType, PostStatus postStatus, Pageable pageRequest, Boolean deleted) {
        return postRepository.findAllByPostTypeAndPostStatusAndDeleted(postType, postStatus, pageRequest, deleted);
    }

    @RequestMapping(value = "/findByIdAndPostStatusAndDeleted", method = RequestMethod.POST)
    @ResponseBody
    Post findByIdAndPostStatusAndDeleted(@RequestParam("postId") Long postId, @RequestBody PostStatus postStatus, @RequestParam("deleted") Boolean deleted) {
        return postRepository.findByIdAndPostStatusAndDeleted(postId, postStatus, deleted);
    }

    @RequestMapping(value = "/findByPermalinkAndPostStatusAndDeleted", method = RequestMethod.POST)
    @ResponseBody
    Post findByPermalinkAndPostStatusAndDeleted(@RequestParam("permalink") String permalink, @RequestBody PostStatus postStatus, @RequestParam("deleted") Boolean deleted) {
        return postRepository.findByPermalinkAndPostStatusAndDeleted(permalink, postStatus, deleted);
    }

    @RequestMapping(value = "/findByTag", method = RequestMethod.POST)
    @ResponseBody
    Page<Post> findByTag(@RequestParam("tag") String tag, @RequestBody Pageable pageable) {
        return postRepository.findByTag(tag, pageable);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.POST)
    @ResponseBody
    Post findOne(@RequestParam("postId") Long postId) {
        return postRepository.findOne(postId);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    Post save(@RequestBody Post post) {
        return postRepository.save(post);
    }

}

