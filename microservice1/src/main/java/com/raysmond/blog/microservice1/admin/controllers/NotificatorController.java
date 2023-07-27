package com.raysmond.blog.microservice1.admin.controllers;

import com.raysmond.blog.common.forms.SettingsForm;
import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.dto.PostAnnouncementDTO;
import com.raysmond.blog.common.models.support.PostStatus;
import com.raysmond.blog.microservice1.client.PostServiceClient;
import com.raysmond.blog.microservice1.notificators.Notificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by bvn13 on 22.12.2017.
 */
@Controller
@RequestMapping(value = "/admin/notify")
public class NotificatorController {

    @Autowired
    private Notificator notificator;


    @Autowired
    private PostServiceClient postService;


    // TODO

    @RequestMapping(value = "testSendTelegramAnnounce", method = RequestMethod.GET)
    @ResponseBody
    public String testSendTelegramAnnounce() {
        Model model = new BindingAwareModelMap();
        sendTelegramAnnounce(1L, model);
        return "test";
    }

    ////////////////////////////////////


    @PostMapping(value = "/{postId:[0-9]+}/telegram", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    PostAnnouncementDTO sendTelegramAnnounce(@PathVariable Long postId, Model model) {

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println("PostAnnouncementDTO: ");
//        System.out.println(RamUsageEstimator.sizeOf(new PostAnnouncementDTO(true, "Post is not published!")));

        Post post = postService.getPost(postId);
        if (post.getPostStatus().equals(PostStatus.PUBLISHED)) {
            try {
                notificator.announcePost(post);
                return new PostAnnouncementDTO(false);
            } catch (IllegalArgumentException iae) {
                iae.printStackTrace();
                return new PostAnnouncementDTO(true, iae.getMessage());
            } catch (TelegramApiException e) {
                e.printStackTrace();
                return new PostAnnouncementDTO(true, "Error occures");
            }
        } else {
            return new PostAnnouncementDTO(true, "Post is not published!");
        }
    }

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Model model = new BindingAwareModelMap();

        if (method.equals("all") || method.equals("sendTelegramAnnounce")) {
            sendTelegramAnnounce_test(1L, model);
        }
        return "test";
    }

    public PostAnnouncementDTO sendTelegramAnnounce_test(@PathVariable Long postId, Model model) {

//        Post post = postService.getPost(postId);
        Post post = new Post();
        if (post.getPostStatus().equals(PostStatus.PUBLISHED)) {
//            try {
//                notificator.announcePost(post);
            return new PostAnnouncementDTO(false);
//            } catch (IllegalArgumentException iae) {
//                iae.printStackTrace();
//                return new PostAnnouncementDTO(true, iae.getMessage());
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//                return new PostAnnouncementDTO(true, "Error occures");
//            }
        } else {
            return new PostAnnouncementDTO(true, "Post is not published!");
        }
    }

}
