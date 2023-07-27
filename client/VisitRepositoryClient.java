package com.raysmond.blog.microservice7.client;

import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.Setting;
import com.raysmond.blog.common.models.Visit;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@FeignClient(name = "springblog-microservice1")
@RequestMapping("/VisitRepositoryController")
public interface VisitRepositoryClient {

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    Visit save(@RequestBody Visit visit);

    @RequestMapping(value = "/getVisitsByPostAndIsAdminIsFalse", method = RequestMethod.POST)
    @ResponseBody
    List<Object> getVisitsByPostAndIsAdminIsFalse(@RequestBody Post post);

}
