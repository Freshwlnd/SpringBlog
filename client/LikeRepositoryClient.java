package com.raysmond.blog.microservice7.client;

import com.raysmond.blog.common.models.Like;
import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.PostUserParams;
import com.raysmond.blog.common.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LikeRepositoryClient {

    @Autowired
    LikeRepositoryRealClient likeRepositoryRealClient;

    public Integer getTotalLikesByUserAndPost(User user, Post post) {
        PostUserParams postUserParams = new PostUserParams(post, user);
        return likeRepositoryRealClient.getTotalLikesByUserAndPost(postUserParams);
    }

    public Integer getTotalLikesByPost(Post post) {
        return likeRepositoryRealClient.getTotalLikesByPost(post);
    }

    public Integer getTotalLikesByClientIpAndPost(String clientIp, Post post) {
        return likeRepositoryRealClient.getTotalLikesByClientIpAndPost(clientIp, post);
    }

    public void save(Like like) {
        likeRepositoryRealClient.save(like);
    }

}
