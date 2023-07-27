package com.raysmond.blog.microservice7.client;

import com.raysmond.blog.common.models.SeoRobotAgent;
import com.raysmond.blog.common.models.Setting;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@FeignClient(name = "springblog-microservice1")
@RequestMapping("/SeoRobotAgentRepositoryController")
public interface SeoRobotAgentRepositoryClient {

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    void delete(@RequestBody SeoRobotAgent ua);

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    List<SeoRobotAgent> findAll();

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ResponseBody
    SeoRobotAgent findOne(@RequestParam("recordId") Long recordId);

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    SeoRobotAgent save(@RequestBody SeoRobotAgent ua);

}
