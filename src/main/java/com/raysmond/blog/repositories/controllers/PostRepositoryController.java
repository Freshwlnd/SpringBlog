package com.raysmond.blog.repositories.controllers;

import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.support.PostStatus;
import com.raysmond.blog.models.support.PostType;
import com.raysmond.blog.repositories.PostRepository;
import com.raysmond.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    PostService postService;

    Post post = null;
    Post deletePost = null;
    PageRequest pageRequest = null;
    PostStatus postStatus = PostStatus.PUBLISHED;
    PostType postType = PostType.POST;
    Boolean deleted = false;
    Long postId = 9L;
    String permalink = "permalink";
    String tag = "tag";

    private void init() {
        post = this.postService.getPost(1L);
        pageRequest = new PageRequest(0, Integer.MAX_VALUE, Sort.Direction.DESC, "createdAt");
        deletePost = new Post();
        deletePost.setId(9L);
        deletePost.setPermalink(permalink);
    }

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
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

    List<Object[]> countPostsByTags(PostStatus status) {
        return postRepository.countPostsByTags(status);
    }

    void delete(Post post) {
        postRepository.delete(post);
    }

    List<Post> findAll() {
        return postRepository.findAll();
    }

    Page<Post> findAllByPostTypeAndPostStatusAndDeleted(PostType postType, PostStatus postStatus, Pageable pageRequest, Boolean deleted) {
        return postRepository.findAllByPostTypeAndPostStatusAndDeleted(postType, postStatus, pageRequest, deleted);
    }

    Post findByIdAndPostStatusAndDeleted(Long postId, PostStatus postStatus, Boolean deleted) {
        return postRepository.findByIdAndPostStatusAndDeleted(postId, postStatus, deleted);
    }

    Post findByPermalinkAndPostStatusAndDeleted(String permalink, PostStatus postStatus, Boolean deleted) {
        return postRepository.findByPermalinkAndPostStatusAndDeleted(permalink, postStatus, deleted);
    }

    Page<Post> findByTag(String tag, Pageable pageable) {
        return postRepository.findByTag(tag, pageable);
    }

    Post findOne(Long postId) {
        return postRepository.findOne(postId);
    }

    Post save(Post post) {
        return postRepository.save(post);
    }

}

