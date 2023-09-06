package com.raysmond.blog.microservice1.client;

import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.PostParams;
import com.raysmond.blog.common.models.RestPage;
import com.raysmond.blog.common.models.Tag;
import com.raysmond.blog.common.models.dto.PostIdTitleDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Repository
@FeignClient(name = "springblog-microservice7")
@RequestMapping("/PostService")
public interface PostServiceRealClient {

    @RequestMapping(value = "/getPost", method = RequestMethod.GET)
    @ResponseBody
    public Post getPost(@RequestParam("postId") Long postId) ;

    @RequestMapping(value = "/getPublishedPost", method = RequestMethod.GET)
    @ResponseBody
    public Post getPublishedPost(@RequestParam("postId") Long postId) ;

    @RequestMapping(value = "/getPublishedPostByPermalink", method = RequestMethod.GET)
    @ResponseBody
    public Post getPublishedPostByPermalink(@RequestParam("permalink") String permalink) ;

    @RequestMapping(value = "/createPost", method = RequestMethod.POST)
    @ResponseBody
    public Post createPost(@RequestBody Post post) ;

    @RequestMapping(value = "/updatePost", method = RequestMethod.POST)
    @ResponseBody
    public Post updatePost(@RequestBody Post post) ;

    @RequestMapping(value = "/deletePost", method = RequestMethod.POST)
    @ResponseBody
    public void deletePost(@RequestBody Post post) ;

    @RequestMapping(value = "/deletePost", method = RequestMethod.GET)
    @ResponseBody
    public void deletePost(@RequestParam("postId") Long postId) ;

    @RequestMapping(value = "/getArchivePosts", method = RequestMethod.GET)
    @ResponseBody
    public List<Post> getArchivePosts() ;

    @RequestMapping(value = "/getPostTags", method = RequestMethod.POST)
    @ResponseBody
    public List<Tag> getPostTags(@RequestBody Post post) ;

    @RequestMapping(value = "/getSeoKeywordsAsString", method = RequestMethod.POST)
    @ResponseBody
    public String getSeoKeywordsAsString(@RequestBody Post post) ;

    @RequestMapping(value = "/getAllPublishedPostsByPage", method = RequestMethod.GET)
    @ResponseBody
    public RestPage<Post> getAllPublishedPostsByPage(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) ;

    @RequestMapping(value = "/getAllPublishedPosts", method = RequestMethod.GET)
    @ResponseBody
    public List<Post> getAllPublishedPosts() ;

    @RequestMapping(value = "/getAllPosts", method = RequestMethod.GET)
    @ResponseBody
    public List<Post> getAllPosts() ;

    @RequestMapping(value = "/createAboutPage", method = RequestMethod.GET)
    @ResponseBody
    public Post createAboutPage() ;

    @RequestMapping(value = "/createProjectsPage", method = RequestMethod.GET)
    @ResponseBody
    public Post createProjectsPage(@RequestParam("needStore") Boolean needStore) ;

    @RequestMapping(value = "/parseTagNames", method = RequestMethod.GET)
    @ResponseBody
    public Set<Tag> parseTagNames(@RequestParam("tagNames") String tagNames) ;

    @RequestMapping(value = "/getTagNames", method = RequestMethod.POST)
    @ResponseBody
    public String getTagNames(@RequestBody Set<Tag> tags) ;

    @RequestMapping(value = "/findPostsByTag", method = RequestMethod.GET)
    @ResponseBody
    public RestPage<Post> findPostsByTag(@RequestParam("tagName") String tagName, @RequestParam("page") int page, @RequestParam("pageSize") int pageSize) ;

    @RequestMapping(value = "/countPostsByTags", method = RequestMethod.GET)
    @ResponseBody
    public List<Object[]> countPostsByTags() ;

    @RequestMapping(value = "/findPostByPermalink", method = RequestMethod.GET)
    @ResponseBody
    public Post findPostByPermalink(@RequestParam("permalink") String permalink) ;

    @RequestMapping(value = "/getPostsIdTitleList", method = RequestMethod.GET)
    @ResponseBody
    public List<PostIdTitleDTO> getPostsIdTitleList() ;

    @RequestMapping(value = "/findAllPosts", method = RequestMethod.POST)
    @ResponseBody
    public Page<Post> findAllPosts(@RequestBody PostParams postPrams) ;

}
