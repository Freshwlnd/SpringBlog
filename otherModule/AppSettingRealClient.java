package com.raysmond.blog.microservice1.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
public class AppSettingClient {

    @Autowired
    AppSettingRealClient appSettingRealClient;

    private String siteName = "SpringBlog";
    private String siteSlogan = "An interesting place to discover";
    private Integer pageSize = 5;
    private String storagePath = "/tmp";
    private String mainUri = "http://localhost/";
    private String telegramMasterChatId = "";

    public static final String SITE_NAME = "site_name";
    public static final String SITE_SLOGAN = "site_slogan";
    public static final String PAGE_SIZE = "page_size";
    public static final String STORAGE_PATH = "storage_path";
    public static final String MAIN_URI = "main_uri";
    public static final String TELEGRAM_MASTER_CHAT_ID = "telegram_master_chat_id";

    public String getSiteName() {
        return appSettingRealClient.getSiteName();
    }

    public void setSiteName(String siteName) {
        appSettingRealClient.setSiteName(siteName);
    }

    public Integer getPageSize() {
        return appSettingRealClient.getPageSize();
    }

    public void setPageSize(Integer pageSize) {
        appSettingRealClient.setPageSize(pageSize);
    }

    public String getSiteSlogan() {
        return appSettingRealClient.getSiteSlogan();
    }

    public void setSiteSlogan(String siteSlogan) {
        appSettingRealClient.setSiteSlogan(siteSlogan);
    }

    public String getStoragePath() {
        return appSettingRealClient.getStoragePath();
    }

    public void setStoragePath(String storagePath) {
        appSettingRealClient.setStoragePath(storagePath);
    }

    public String getMainUri() {
        return appSettingRealClient.getMainUri();
    }

    public String getMainUriStripped() {
        return appSettingRealClient.getMainUriStripped();
    }

    public void setMainUri(String mainUri) {
        appSettingRealClient.setMainUri(mainUri);
    }

    public String getTelegramMasterChatId() {
        return appSettingRealClient.getTelegramMasterChatId();
    }

    public void setTelegramMasterChatId(String id) {
        appSettingRealClient.setTelegramMasterChatId(id);
    }

    public List<String> getOgLocales() {
        return appSettingRealClient.getOgLocales();
    }

    public List<String> getOgTypes() {
        return appSettingRealClient.getOgTypes();
    }

}
