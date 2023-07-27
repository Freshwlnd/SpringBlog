package com.raysmond.blog.microservice6.client;

import com.raysmond.blog.common.models.Setting;
import com.raysmond.blog.common.models.StoredFile;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@FeignClient(name = "springblog-microservice1")
@RequestMapping("/SettingRepositoryController")
public interface SettingRepositoryClient {
    
    @RequestMapping(value = "/findByKey", method = RequestMethod.GET)
    @ResponseBody
    Setting findByKey(@RequestParam("key") String key);

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    Setting save(@RequestParam("setting") Setting setting);;

}
