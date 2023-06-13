package com.raysmond.blog.microservice2.seo.controllers;

import com.raysmond.blog.microservice2.models.Post;
import com.raysmond.blog.microservice2.services.PostService;
import com.raysmond.blog.microservice2.services.SeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = {"/seo"})
public class SitemapController {

    @Autowired
    private PostService postService;

    @Autowired
    private SeoService seoService;

    @GetMapping(value = "/sitemap", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    String getSiteMap() {
        List<Post> posts = this.postService.getAllPublishedPosts();
        return this.seoService.createSitemap(posts);
    }

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {

        if (method.equals("all") || method.equals("getSiteMap")) {
            getSiteMap_test();
        }

        return "test";
    }

    String getSiteMap_test() {
//        List<Post> posts = this.postService.getAllPublishedPosts();
        List<Post> posts = new ArrayList<>();
//        return this.seoService.createSitemap(posts);
        return "test";
    }

}
