package com.raysmond.blog.microservice7.client;

import com.raysmond.blog.common.models.SeoPostData;
import com.raysmond.blog.common.models.SeoRobotAgent;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@FeignClient(name = "springblog-microservice1")
@RequestMapping("/SeoPostDataRepositoryController")
public interface SeoPostDataRepositoryClient {

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    void save(@RequestBody SeoPostData data);

}
