package com.raysmond.blog.microservice1.client;

import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.PostUserParams;
import com.raysmond.blog.common.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "springblog-microservice7")
@RequestMapping("/LikeService")
public interface LikeServiceRealClient {

    @RequestMapping(value = "/getTotalLikesByPost", method = RequestMethod.POST)
    @ResponseBody
    public Integer getTotalLikesByPost(@RequestBody Post post);

    @RequestMapping(value = "/getTotalLikesByUserAndPost", method = RequestMethod.POST)
    @ResponseBody
    public Integer getTotalLikesByUserAndPost(@RequestBody PostUserParams postUserParams);

    @RequestMapping(value = "/likePost", method = RequestMethod.POST)
    @ResponseBody
    public void likePost(@RequestBody Post post, @RequestParam("clientIp") String clientIp);

    @RequestMapping(value = "/dislikePost", method = RequestMethod.POST)
    @ResponseBody
    public void dislikePost(@RequestBody Post post, @RequestParam("clientIp") String clientIp);

}
