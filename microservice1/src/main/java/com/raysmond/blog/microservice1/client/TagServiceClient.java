package com.raysmond.blog.microservice1.client;

import com.raysmond.blog.common.models.Tag;
import com.raysmond.blog.common.models.User;

import java.util.List;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "springblog-microservice7")
@RequestMapping("/TagService")
public interface TagServiceClient {

    @RequestMapping(value = "/findOrCreateByName", method = RequestMethod.GET)
    @ResponseBody
    public Tag findOrCreateByName(@RequestParam("name") String name);

    @RequestMapping(value = "/getTag", method = RequestMethod.GET)
    @ResponseBody
    public Tag getTag(@RequestParam("tagName") String tagName);

    @RequestMapping(value = "/deleteTag", method = RequestMethod.POST)
    @ResponseBody
    public void deleteTag(@RequestBody Tag tag);

    @RequestMapping(value = "/getAllTags", method = RequestMethod.GET)
    @ResponseBody
    public List<Tag> getAllTags();

}
