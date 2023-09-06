package com.raysmond.blog.microservice7.client;

import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.PostParams;
import com.raysmond.blog.common.models.Visit;
import com.raysmond.blog.common.models.support.PostStatus;
import com.raysmond.blog.common.models.support.PostType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
// import com.raysmond.blog.common.models.PageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PostRepositoryClient {

    @Autowired
    PostRepositoryRealClient postRepositoryRealClient;

    public List<Post> findAllByPostTypeAndPostStatusAndDeleted(PostType postType, PostStatus postStatus, Boolean deleted) {
        PostParams postPrams = new PostParams(postType, postStatus, deleted);
        return postRepositoryRealClient.findAllByPostTypeAndPostStatusAndDeletedList(postPrams);
    }

    public Page<Post> findAllByDeleted(Pageable pageRequest, Boolean deleted) {
        PostParams postPrams = new PostParams(pageRequest, deleted);
        return postRepositoryRealClient.findAllByDeleted(postPrams);
    }

    public List<Object[]> countPostsByTags(PostStatus status) {
        return postRepositoryRealClient.countPostsByTags(status);
    }

    public void delete(Post post) {
        postRepositoryRealClient.delete(post);
    }

    public List<Post> findAll() {
        return postRepositoryRealClient.findAll();
    }

    public Page<Post> findAllByPostTypeAndPostStatusAndDeleted(PostType postType, PostStatus postStatus, Pageable pageRequest, Boolean deleted) {
    // public Page<Post> findAllByPostTypeAndPostStatusAndDeleted(PostType postType, PostStatus postStatus, PageRequest pageRequest, Boolean deleted) {
        PostParams postPrams = new PostParams(postType, postStatus, pageRequest, deleted);
        return postRepositoryRealClient.findAllByPostTypeAndPostStatusAndDeleted(postPrams);
    }

    public Post findByIdAndPostStatusAndDeleted(Long postId, PostStatus postStatus, Boolean deleted) {
        return postRepositoryRealClient.findByIdAndPostStatusAndDeleted(postId, postStatus, deleted);
    }

    public Post findByPermalinkAndPostStatusAndDeleted(String permalink, PostStatus postStatus, Boolean deleted) {
        return postRepositoryRealClient.findByPermalinkAndPostStatusAndDeleted(permalink, postStatus, deleted);
    }

    public Page<Post> findByTag(String tag, Pageable pageable) {
    // public Page<Post> findByTag(String tag, PageRequest pageable) {
        PostParams postPrams = new PostParams(tag, pageable);
//        return postRepositoryRealClient.findByTag(tag, postPrams);
        return postRepositoryRealClient.findByTag(postPrams);
    }

    public Post findOne(Long postId) {
        return postRepositoryRealClient.findOne(postId);
    }

    public Post save(Post post) {
        return postRepositoryRealClient.save(post);
    }

}
