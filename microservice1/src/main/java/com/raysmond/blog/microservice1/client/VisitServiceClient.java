package com.raysmond.blog.microservice1.client;

import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.Tag;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Repository
@FeignClient(name = "springblog-microservice7")
@RequestMapping("/VisitService")
public interface VisitServiceClient {

    @RequestMapping(value = "/saveVisit", method = RequestMethod.POST)
    @ResponseBody
    public void saveVisit(@RequestBody Post post, @RequestParam("clientIp") String clientIp, @RequestParam("userAgent") String userAgent);

    @RequestMapping(value = "/getUniqueVisitsCount", method = RequestMethod.POST)
    @ResponseBody
    public Long getUniqueVisitsCount(@RequestBody Post post);

    @RequestMapping(value = "/getUniqueVisitsCount_old", method = RequestMethod.POST)
    @ResponseBody
    public Long getUniqueVisitsCount_old(@RequestBody Post post);

}
