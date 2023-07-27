package com.raysmond.blog.microservice7.client;

import com.raysmond.blog.common.models.Like;
import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.PostUserParams;
import com.raysmond.blog.common.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@FeignClient(name = "springblog-microservice1")
@RequestMapping("/LikeRepositoryController")
public interface LikeRepositoryRealClient {

    @RequestMapping(value = "/getTotalLikesByUserAndPost", method = RequestMethod.POST)
    Integer getTotalLikesByUserAndPost(@RequestBody PostUserParams postUserParams);

    @RequestMapping(value = "/getTotalLikesByPost", method = RequestMethod.POST)
    Integer getTotalLikesByPost(@RequestBody Post post);

    @RequestMapping(value = "/getTotalLikesByClientIpAndPost", method = RequestMethod.POST)
    Integer getTotalLikesByClientIpAndPost(@RequestParam("clientIp") String clientIp, @RequestBody Post post);

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    void save(@RequestBody Like like);

}
