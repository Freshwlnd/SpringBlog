package com.raysmond.blog.microservice1.notificators;

import com.raysmond.blog.microservice1.client.AppSettingClient;
import com.raysmond.blog.microservice1.notificators.telegram.TelegramBotManager;
import com.raysmond.blog.common.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by bvn13 on 21.12.2017.
 */
@RequestMapping("/notificator")
@Component
public class Notificator {

    @Autowired
    private AppSettingClient appSetting;

    @Autowired
    private TelegramBotManager telegramBot;


    @RequestMapping(value = "/announcePost", method = RequestMethod.POST)
    public void announcePost(@RequestBody Post post) throws IllegalArgumentException, TelegramApiException {
        if (post == null || post.getAnnouncement().isEmpty()) {
            throw new IllegalArgumentException("Nothing to announce");
        }
        String postUrl = appSetting.getMainUriStripped() + "/posts/" + (post.getPermalink().isEmpty() ? post.getId() : post.getPermalink());
        String message = String.format(
                "*%s*\r\n\n" +
                        "%s\r\n\r\n" +
                        "[%s](%s)",
                post.getTitle(),
                post.getAnnouncement() != null ? post.getAnnouncement() : "",
                postUrl, postUrl
        );
        telegramBot.sendMessageToChannel(message);
    }


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) throws IllegalArgumentException, TelegramApiException {
        Post post = new Post();
        post.init();

        if (method.equals("all") || method.equals("announcePost")) {
            announcePost_test(post);
        }

        return "test";
    }

    public void announcePost_test(Post post) throws IllegalArgumentException, TelegramApiException {
        if (post == null || post.getAnnouncement().isEmpty()) {
//            throw new IllegalArgumentException("Nothing to announce");
        }
//        String postUrl = appSetting.getMainUriStripped()+"/posts/"+(post.getPermalink().isEmpty() ? post.getId() : post.getPermalink());
        String postUrl = "/posts/" + (post.getPermalink().isEmpty() ? post.getId() : post.getPermalink());
        String message = String.format(
                "*%s*\r\n\n" +
                        "%s\r\n\r\n" +
                        "[%s](%s)",
                post.getTitle(),
                post.getAnnouncement() != null ? post.getAnnouncement() : "",
                postUrl, postUrl
        );
//        telegramBot.sendMessageToChannel(message);
    }

}
