package com.raysmond.blog.microservice1.client;

import com.raysmond.blog.common.models.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "springblog-microservice7")
@RequestMapping("/FlexmarkMarkdownService")
public interface MarkdownServiceClient {
    
    @RequestMapping(value = "/renderToHtml", method = RequestMethod.GET)
    public String renderToHtml(@RequestParam("content") String content);

    @RequestMapping(value = "/highlight", method = RequestMethod.GET)
    public String highlight(@RequestParam("content") String content);

}
