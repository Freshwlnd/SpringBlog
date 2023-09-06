package com.raysmond.blog.microservice1.client;

import com.raysmond.blog.common.models.*;
import com.raysmond.blog.common.models.dto.PostIdTitleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
// import com.raysmond.blog.common.models.PageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceClient {

    @Autowired
    PostServiceRealClient postServiceRealClient;

    public Post getPost(Long postId) {
        return this.postServiceRealClient.getPost(postId);
    }

    public Post getPublishedPost(Long postId) {
        return this.postServiceRealClient.getPublishedPost(postId);
    }

    public Post getPublishedPostByPermalink(String permalink) {
        return this.postServiceRealClient.getPublishedPostByPermalink(permalink);
    }

    public Post createPost(Post post) {
        return this.postServiceRealClient.createPost(post);
    }

    public Post updatePost(Post post) {
        return this.postServiceRealClient.updatePost(post);
    }

    public void deletePost(Post post) {
        this.postServiceRealClient.deletePost(post);
    }

    public void deletePost(Long postId) {
        this.postServiceRealClient.deletePost(postId);
    }

    public List<Post> getArchivePosts() {
        return this.postServiceRealClient.getArchivePosts();
    }

    public List<Tag> getPostTags(Post post) {
        return this.postServiceRealClient.getPostTags(post);
    }

    public String getSeoKeywordsAsString(Post post) {
        return this.postServiceRealClient.getSeoKeywordsAsString(post);
    }

    public RestPage<Post> getAllPublishedPostsByPage(Integer page, Integer pageSize) {
        return this.postServiceRealClient.getAllPublishedPostsByPage(page, pageSize);
    }

    public List<Post> getAllPublishedPosts() {
        return this.postServiceRealClient.getAllPublishedPosts();
    }

    public List<Post> getAllPosts() {
        return this.postServiceRealClient.getAllPosts();
    }

    public Post createAboutPage() {
        return this.postServiceRealClient.createAboutPage();
    }

    public Post createProjectsPage(Boolean needStore) {
        return this.postServiceRealClient.createProjectsPage(needStore);
    }

    public Set<Tag> parseTagNames(String tagNames) {
        return this.postServiceRealClient.parseTagNames(tagNames);
    }

    public String getTagNames(Set<Tag> tags) {
        return this.postServiceRealClient.getTagNames(tags);
    }

    public RestPage<Post> findPostsByTag(String tagName, int page, int pageSize) {
        return this.postServiceRealClient.findPostsByTag(tagName, page, pageSize);
    }

    public List<Object[]> countPostsByTags() {
        return this.postServiceRealClient.countPostsByTags();
    }

    public Post findPostByPermalink(String permalink) {
        return this.postServiceRealClient.findPostByPermalink(permalink);
    }

    public List<PostIdTitleDTO> getPostsIdTitleList() {
        return this.postServiceRealClient.getPostsIdTitleList();
    }

    public Page<Post> findAllPosts(PageRequest pageRequest) {
        return this.postServiceRealClient.findAllPosts(new PostParams(pageRequest));
    }

}
