package com.raysmond.blog.services;

import com.raysmond.blog.services.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.domingosuarez.boot.autoconfigure.jade4j.JadeHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raysmond<i@raysmond.com>
 */
@JadeHelper("App")
@Service
@RequestMapping("/AppSetting")
public class AppSetting {

    private SettingService settingService;

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


    @Autowired
    public AppSetting(SettingService settingService){
        this.settingService = settingService;
    }

    public String getSiteName(){
        return (String) settingService.get(SITE_NAME, siteName);
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
        settingService.put(SITE_NAME, siteName);
    }

    public Integer getPageSize() {
        return (Integer) settingService.get(PAGE_SIZE, pageSize);
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        settingService.put(PAGE_SIZE, pageSize);
    }

    public String getSiteSlogan() {
        return (String) settingService.get(SITE_SLOGAN, siteSlogan);
    }

    public void setSiteSlogan(String siteSlogan) {
        this.siteSlogan = siteSlogan;
        settingService.put(SITE_SLOGAN, siteSlogan);
    }

    public String getStoragePath() {
        return (String) settingService.get(STORAGE_PATH, storagePath);
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
        settingService.put(STORAGE_PATH, storagePath);
    }

    public String getMainUri() {
        String uri = (String) settingService.get(MAIN_URI, mainUri);
        if (!uri.endsWith("/")) {
            uri += "/";
        }
        return uri;
    }

    public String getMainUriStripped() {
        String uri = (String) settingService.get(MAIN_URI, mainUri);
        if (uri.endsWith("/")) {
            uri = uri.substring(0, uri.length()-1);
        }
        return uri;
    }

    public void setMainUri(String mainUri) {
        this.mainUri = mainUri;
        settingService.put(MAIN_URI, mainUri);
    }

    public String getTelegramMasterChatId() {
        String id = (String) settingService.get(TELEGRAM_MASTER_CHAT_ID, "");
        return id;
    }

    public void setTelegramMasterChatId(String id) {
        this.telegramMasterChatId = id;
        settingService.put(TELEGRAM_MASTER_CHAT_ID, id);
    }

    public List<String> getOgLocales() {
        ArrayList<String> ogLocales = new ArrayList<>();
        ogLocales.add("en_EN");
        ogLocales.add("ru_RU");
        return ogLocales;
    }

    public List<String> getOgTypes() {
        ArrayList<String> ogTypes = new ArrayList<>();
        ogTypes.add("article");
        return ogTypes;
    }


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        String mainUri = "localhost:8080/";
        Integer pageSize = 1;
        String storagePath = "/tmp";
        String siteSlogan = "siteSlogan";
        String id = "1";

        switch (method) {
            case "all":
                for (int i = 0; i < 1; i++) {
                    getMainUri_test();
                }
                for (int i = 0; i < 1; i++) {
                    setMainUri_test(mainUri);
                }
                for (int i = 0; i < 1; i++) {
                    getMainUriStripped_test();
                }
                for (int i = 0; i < 2; i++) {
                    getPageSize_test();
                }
                for (int i = 0; i < 1; i++) {
                    setPageSize_test(pageSize);
                }
                for (int i = 0; i < 3; i++) {
                    getStoragePath_test();
                }
                for (int i = 0; i < 1; i++) {
                    setStoragePath_test(storagePath);
                }
                for (int i = 0; i < 1; i++) {
                    setSiteName_test(siteName);
                }
                for (int i = 0; i < 1; i++) {
                    setSiteSlogan_test(siteSlogan);
                }
                for (int i = 0; i < 1; i++) {
                    setTelegramMasterChatId_test(id);
                }
                break;
            case "getMainUri":
                getMainUri_test();
                break;
            case "setMainUri":
                setMainUri_test(mainUri);
                break;
            case "getMainUriStripped":
                getMainUriStripped_test();
                break;
            case "getPageSize":
                getPageSize_test();
                break;
            case "setPageSize":
                setPageSize_test(pageSize);
                break;
            case "getStoragePath":
                getStoragePath_test();
                break;
            case "setStoragePath":
                setStoragePath_test(storagePath);
                break;
            case "setSiteName":
                setSiteName_test(siteName);
                break;
            case "setSiteSlogan":
                setSiteSlogan_test(siteSlogan);
                break;
            case "setTelegramMasterChatId":
                setTelegramMasterChatId_test(id);
                break;
        }

        return "test";
    }

    public String getMainUri_test() {
//        String uri = (String) settingService.get(MAIN_URI, mainUri);
        String uri = "localhost:8080";
        if (!uri.endsWith("/")) {
            uri += "/";
        }
        return uri;
    }

    public void setMainUri_test(String mainUri) {
        this.mainUri = mainUri;
//        settingService.put(MAIN_URI, mainUri);
    }

    public String getMainUriStripped_test() {
//        String uri = (String) settingService.get(MAIN_URI, mainUri);
        String uri = "localhost:8080/";
        if (uri.endsWith("/")) {
            uri = uri.substring(0, uri.length()-1);
        }
        return uri;
    }

    public Integer getPageSize_test() {
//        return (Integer) settingService.get(PAGE_SIZE, pageSize);
        return 1;
    }

    public void setPageSize_test(Integer pageSize) {
        this.pageSize = pageSize;
//        settingService.put(PAGE_SIZE, pageSize);
    }

    public String getStoragePath_test() {
//        return (String) settingService.get(STORAGE_PATH, storagePath);
        return "test";
    }

    public void setStoragePath_test(String storagePath) {
        this.storagePath = storagePath;
//        settingService.put(STORAGE_PATH, storagePath);
    }

    public void setSiteName_test(String siteName) {
        this.siteName = siteName;
//        settingService.put(SITE_NAME, siteName);
    }

    public void setSiteSlogan_test(String siteSlogan) {
        this.siteSlogan = siteSlogan;
//        settingService.put(SITE_SLOGAN, siteSlogan);
    }

    public void setTelegramMasterChatId_test(String id) {
        this.telegramMasterChatId = id;
//        settingService.put(TELEGRAM_MASTER_CHAT_ID, id);
    }

}
