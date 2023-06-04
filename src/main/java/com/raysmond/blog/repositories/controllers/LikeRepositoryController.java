package com.raysmond.blog.repositories.controllers;

import com.raysmond.blog.models.Like;
import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.User;
import com.raysmond.blog.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/LikeRepositoryController")
public class LikeRepositoryController {

    @Autowired
    LikeRepository likeRepository;

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Post post = new Post();
        User user = new User();
        String clientIp = "10.60.150.33";
        Like like = new Like();
        like.setClientIp(clientIp);
        like.setPost(post);
        like.setUser(user);
        like.setIsAdmin(user.isAdmin());
        like.setSympathy(1);


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


    Integer getTotalLikesByUserAndPost(User user,Post post) {
        return likeRepository.getTotalLikesByUserAndPost(user, post);
    }

    Integer getTotalLikesByPost(Post post) {
        return likeRepository.getTotalLikesByPost(post);
    }

    Integer getTotalLikesByClientIpAndPost(String clientIp, Post post) {
        return likeRepository.getTotalLikesByClientIpAndPost(clientIp, post);
    }

    void save(Like like) {
        likeRepository.save(like);
    }

}
