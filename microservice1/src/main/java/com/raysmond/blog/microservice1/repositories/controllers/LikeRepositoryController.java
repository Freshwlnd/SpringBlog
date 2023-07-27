package com.raysmond.blog.microservice1.repositories.controllers;

import com.raysmond.blog.common.models.PostUserParams;
import com.raysmond.blog.common.models.Like;
import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.User;
import com.raysmond.blog.microservice1.client.PostServiceClient;
import com.raysmond.blog.microservice1.client.UserServiceClient;
import com.raysmond.blog.microservice1.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/LikeRepositoryController")
public class LikeRepositoryController {
    @Autowired
    LikeRepository likeRepository;

    @Autowired
    UserServiceClient userService;

    @Autowired
    PostServiceClient postService;

    User user;
    Post post;
    String clientIp;
    Like like;

    private void init() {
        user = this.userService.getSuperUser();
        post = this.postService.getPost(1L);
        clientIp = "10.60.150.33";
        try {
            like = likeRepository.findAll().get(0);
        } catch (Exception e) {
            like = new Like();
            like.setClientIp(clientIp);
            like.setPost(post);
            like.setUser(user);
            like.setIsAdmin(user.isAdmin());
            like.setSympathy(1);
            like = likeRepository.save(like);
        }
        if (like == null) {
            like = new Like();
            like.setClientIp(clientIp);
            like.setPost(post);
            like.setUser(user);
            like.setIsAdmin(user.isAdmin());
            like.setSympathy(1);
            like = likeRepository.save(like);
        }
    }

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        if (user == null) {
            init();
        }


        switch (method) {
            case "all":
                for (int i = 0; i < 2; i++) {
                    getTotalLikesByClientIpAndPost(clientIp, post);
                }
                for (int i = 0; i < 5; i++) {
                    getTotalLikesByPost(post);
                }
                for (int i = 0; i < 2; i++) {
                    getTotalLikesByUserAndPost(user, post);
                }
                for (int i = 0; i < 2; i++) {
                    save(like);
                }
                break;
            case "getTotalLikesByUserAndPost":
                getTotalLikesByUserAndPost(user, post);
                break;
            case "getTotalLikesByPost":
                getTotalLikesByPost(post);
                break;
            case "getTotalLikesByClientIpAndPost":
                getTotalLikesByClientIpAndPost(clientIp, post);
                break;
            case "save":
                save(like);
                break;
        }

        return "test";
    }

    @RequestMapping(value = "/getTotalLikesByUserAndPost", method = RequestMethod.POST)
    Integer getTotalLikesByUserAndPost(@RequestBody PostUserParams postUserParams) {
        User user = postUserParams.getUser();
        Post post = postUserParams.getPost();
        return likeRepository.getTotalLikesByUserAndPost(user, post);
    }

    Integer getTotalLikesByUserAndPost(User user, Post post) {
        return likeRepository.getTotalLikesByUserAndPost(user, post);
    }

    @RequestMapping(value = "/getTotalLikesByPost", method = RequestMethod.POST)
    Integer getTotalLikesByPost(@RequestBody Post post) {
        return likeRepository.getTotalLikesByPost(post);
    }

    @RequestMapping(value = "/getTotalLikesByClientIpAndPost", method = RequestMethod.POST)
    Integer getTotalLikesByClientIpAndPost(@RequestParam("clientIp") String clientIp, @RequestBody Post post) {
        return likeRepository.getTotalLikesByClientIpAndPost(clientIp, post);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    void save(@RequestBody Like like) {
        likeRepository.save(like);
    }

}
