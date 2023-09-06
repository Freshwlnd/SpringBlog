package com.raysmond.blog.microservice7.client;

import com.raysmond.blog.common.models.Like;
import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.PostUserParams;
import com.raysmond.blog.common.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@FeignClient(name = "springblog-microservice1")
@RequestMapping("/LikeRepositoryController")
public interface LikeRepositoryRealClient {

    @RequestMapping(value = "/getTotalLikesByUserAndPost", method = RequestMethod.POST)
    @ResponseBody
    Integer getTotalLikesByUserAndPost(@RequestBody PostUserParams postUserParams);

    @RequestMapping(value = "/getTotalLikesByPost", method = RequestMethod.POST)
    @ResponseBody
    Integer getTotalLikesByPost(@RequestBody Post post);

    @RequestMapping(value = "/getTotalLikesByClientIpAndPost", method = RequestMethod.POST)
    @ResponseBody
    Integer getTotalLikesByClientIpAndPost(@RequestParam("clientIp") String clientIp, @RequestBody Post post);

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    void save(@RequestBody Like like);

}
