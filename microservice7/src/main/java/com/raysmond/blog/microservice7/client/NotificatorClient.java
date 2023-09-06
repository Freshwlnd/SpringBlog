package com.raysmond.blog.microservice7.client;

import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.SeoRobotAgent;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;

@Controller
@FeignClient(name = "springblog-microservice1")
@RequestMapping("/notificator")
public interface NotificatorClient {

    @RequestMapping(value = "/announcePost", method = RequestMethod.POST)
    @ResponseBody
    public void announcePost(@RequestBody Post post) throws IllegalArgumentException, TelegramApiException;

}
