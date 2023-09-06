package com.raysmond.blog.microservice4.client;

import com.raysmond.blog.common.models.StoredFile;
import com.raysmond.blog.common.models.User;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Controller
@FeignClient(name = "springblog-microservice1")
@RequestMapping("/StoredFileRepositoryController")
public interface StoredFileRepositoryClient {

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    void delete(@RequestBody StoredFile storedFile);

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    List<StoredFile> findAll();

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody
    StoredFile findById(@RequestParam("id") Long id);

    @RequestMapping(value = "/findByName", method = RequestMethod.GET)
    @ResponseBody
    StoredFile findByName(@RequestParam("name") String name);

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    StoredFile save(@RequestBody StoredFile storedFile);

    @RequestMapping(value = "/saveAndFlush", method = RequestMethod.POST)
    @ResponseBody
    StoredFile saveAndFlush(@RequestBody StoredFile storedFile);

}
