package com.raysmond.blog.microservice7.client;

import com.raysmond.blog.common.models.Tag;
import com.raysmond.blog.common.models.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@FeignClient(name = "springblog-microservice1")
@RequestMapping("/TagRepositoryController")
public interface TagRepositoryClient {

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    void delete(@RequestBody Tag tag);

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    List<Tag> findAll();

    @RequestMapping(value = "/findByName", method = RequestMethod.POST)
    @ResponseBody
    Tag findByName(@RequestParam("name") String name);

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    Tag save(@RequestBody Tag tag);

}
