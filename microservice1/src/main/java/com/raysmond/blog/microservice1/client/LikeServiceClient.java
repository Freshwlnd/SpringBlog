package com.raysmond.blog.microservice1.client;

import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.PostUserParams;
import com.raysmond.blog.common.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
public class LikeServiceClient {

    @Autowired
    LikeServiceRealClient likeServiceRealClient;

    public Integer getTotalLikesByPost(Post post) {
        return likeServiceRealClient.getTotalLikesByPost(post);
    }

    public Integer getTotalLikesByUserAndPost(User user, Post post) {
        PostUserParams postUserParams = new PostUserParams(post, user);
        return likeServiceRealClient.getTotalLikesByUserAndPost(postUserParams);
    }

    public void likePost(Post post, String clientIp) {
        likeServiceRealClient.likePost(post, clientIp);
    }

    public void dislikePost(Post post, String clientIp) {
        likeServiceRealClient.dislikePost(post, clientIp);
    }

}
