package com.raysmond.blog.microservice3.notificators.telegram;

import com.raysmond.blog.microservice3.services.AppSetting;
import com.raysmond.blog.microservice3.services.TelegramBotSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by bvn13 on 21.12.2017.
 */
@RequestMapping("/telegramBotManager")
@Component
public class TelegramBotManager {

    @Autowired
    private AppSetting appSetting;

    // https://core.telegram.org/bots/api#sendmessage

    public static class TelegramBot extends TelegramLongPollingBot {

        private static Logger logger = LoggerFactory.getLogger(TelegramBot.class);

        private TelegramBotManager manager;
        private String name;
        private String token;
        private String masterName;
        private String masterChatId;


        public TelegramBot(TelegramBotManager manager, String name, String token, String masterName, String masterChatId) {
            this.manager = manager;
            this.name = name;
            this.token = token;
            this.masterName = masterName;
            this.masterChatId = masterChatId;
        }


        @Override
        public void onUpdateReceived(Update update) {
            // We check if the update has a message and the message has text
            if (update.hasMessage() && update.getMessage().hasText()) {
                logger.debug("CHAT ID: " + update.getMessage().getChatId().toString() + " / MESSAGE: " + update.getMessage().getText());

                Long chatId = update.getMessage().getChatId();

                if (update.getMessage().getChat().getUserName().equals(masterName)) {
                    if (update.getMessage().getText().startsWith("/master")) {
                        this.manager.appSetting.setTelegramMasterChatId(chatId.toString());
                        this._send("Master chat ID changed to: " + chatId.toString(), chatId);
                    } else {
                        this._send("Hello, master!", chatId);
                    }
                } else {
                    this._send("You are not allowed to touch me!", chatId);
                }
            }
        }


        @Override
        public String getBotUsername() {
            return this.name;
        }

        @Override
        public String getBotToken() {
            return this.token;
        }

        public void sendMessageToChannel(String channelName, String message) throws TelegramApiException {
            this.send(message, "@" + channelName);
        }

        public void sendMessageToMaster(String message) throws TelegramApiException {
            this.send(message, this.masterChatId);
        }

        public void send(String message, String chatName) throws TelegramApiException {
            SendMessage sm = new SendMessage();
            sm.setChatId(chatName);
            sm.enableMarkdown(true);
            sm.enableWebPagePreview();
            sm.setText(message);
            this.execute(sm);
        }

        public void send(String message, Long chatId) throws TelegramApiException {
            SendMessage sm = new SendMessage();
            sm.setChatId(chatId);
            sm.enableMarkdown(true);
            sm.enableWebPagePreview();
            sm.setText(message);
            this.execute(sm);
        }

        private void _send(String message, String chatName) {
            try {
                this.send(message, chatName);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        private void _send(String message, Long chatId) {
            try {
                this.send(message, chatId);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private TelegramBotSettings settings;
    private TelegramBot bot;

    private Boolean isActive = false;

    @Autowired
    public TelegramBotManager(TelegramBotSettings settings) {
        this.settings = settings;
    }


    @PostConstruct
    public void startBot() {
        if (settings.isActive()) {

            ApiContextInitializer.init();
            TelegramBotsApi botsApi = new TelegramBotsApi();

            this.bot = new TelegramBot(this,
                    settings.getBotName(),
                    settings.getBotToken(),
                    settings.getMasterName(),
                    appSetting.getTelegramMasterChatId()
            );

            try {
                botsApi.registerBot(this.bot);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            this.isActive = true;

            try {
                if (appSetting.getTelegramMasterChatId() != null && !appSetting.getTelegramMasterChatId().isEmpty()) {
                    sendMessageToMaster("i'm online");
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @PreDestroy
    public void stopBot() {
        if (isActive) {
//            try {
//                sendMessageToMaster("i'm shutting down");
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
        }
    }


    public void sendMessageToChannel(String message) throws TelegramApiException {
        if (isActive) {
            bot.sendMessageToChannel(settings.getBotChannel(), message);
        }
    }

    public void sendMessageToMaster(String message) throws TelegramApiException {
        if (isActive) {
            bot.sendMessageToMaster(message);
        }
    }


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) throws TelegramApiException {
        String message = "message";

        if (method.equals("all") || method.equals("sendMessageToChannel")) {
            sendMessageToChannel_test(message);
        }

        return "test";
    }

    public void sendMessageToChannel_test(String message) throws TelegramApiException {
        if (isActive) {
//            bot.sendMessageToChannel(settings.getBotChannel(), message);
        }
    }


}
