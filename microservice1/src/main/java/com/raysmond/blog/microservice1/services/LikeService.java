package com.raysmond.blog.microservice1.services;

import com.raysmond.blog.microservice1.repositories.LikeRepository;
import com.raysmond.blog.common.models.Like;
import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserService userService;


    public Integer getTotalLikesByPost(Post post) {
        return this.likeRepository.getTotalLikesByPost(post);
    }

    public Integer getTotalLikesByUserAndPost(User user, Post post) {
        return this.likeRepository.getTotalLikesByUserAndPost(user, post);
    }

    public void likePost(Post post, String clientIp) {
        User user = this.userService.currentUser();
        Integer currentSympathy = 0;

        if (user != null) {
            currentSympathy = this.likeRepository.getTotalLikesByUserAndPost(user, post);
        } else {
            currentSympathy = this.likeRepository.getTotalLikesByClientIpAndPost(clientIp, post);
        }
        if (currentSympathy == null) currentSympathy = 0;

        Integer sympathyDelta = -currentSympathy + 1;
        this.saveSympathy(post, user, clientIp, sympathyDelta);
    }

    public void dislikePost(Post post, String clientIp) {
        User user = this.userService.currentUser();
        Integer currentSympathy = 0;

        if (user != null) {
            currentSympathy = this.likeRepository.getTotalLikesByUserAndPost(user, post);
        } else {
            currentSympathy = this.likeRepository.getTotalLikesByClientIpAndPost(clientIp, post);
        }
        if (currentSympathy == null) currentSympathy = 0;

        Integer sympathyDelta = -currentSympathy - 1;
        this.saveSympathy(post, user, clientIp, sympathyDelta);
    }

    private void saveSympathy(Post post, User user, String clientIp, Integer sympathy) {
        Like like = new Like();
        like.setClientIp(clientIp);
        like.setPost(post);
        like.setUser(user);
        like.setIsAdmin(user != null ? user.isAdmin() : false);
        like.setSympathy(sympathy);
        this.likeRepository.save(like);
    }

}
