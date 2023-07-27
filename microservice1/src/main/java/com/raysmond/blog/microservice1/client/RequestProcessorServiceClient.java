package com.raysmond.blog.microservice1.client;

import com.raysmond.blog.common.models.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Repository
@FeignClient(name = "springblog-microservice5")
@RequestMapping("/RequestProcessorService")
public interface RequestProcessorServiceClient {

    @RequestMapping(value = "/getRealIp", method = RequestMethod.POST)
    @ResponseBody
    public String getRealIp(@RequestBody HttpServletRequest request);

    @RequestMapping(value = "/getUserAgent", method = RequestMethod.POST)
    @ResponseBody
    public String getUserAgent(@RequestBody HttpServletRequest request);

}
