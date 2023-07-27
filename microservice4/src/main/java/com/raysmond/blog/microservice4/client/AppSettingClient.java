package com.raysmond.blog.microservice4.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
@FeignClient(name = "springblog-microservice6")
@RequestMapping("/AppSetting")
public interface AppSettingClient {

    @RequestMapping(value = "/getSiteName", method = RequestMethod.GET)
    public String getSiteName() ;

    @RequestMapping(value = "/setSiteName", method = RequestMethod.GET)
    public void setSiteName(@RequestParam("siteName") String siteName) ;

    @RequestMapping(value = "/getPageSize", method = RequestMethod.GET)
    public Integer getPageSize() ;

    @RequestMapping(value = "/setPageSize", method = RequestMethod.POST)
    public void setPageSize(@RequestParam("pageSize") Integer pageSize) ;

    @RequestMapping(value = "/getSiteSlogan", method = RequestMethod.GET)
    public String getSiteSlogan() ;

    @RequestMapping(value = "/setSiteSlogan", method = RequestMethod.GET)
    public void setSiteSlogan(@RequestParam("siteSlogan") String siteSlogan) ;

    @RequestMapping(value = "/getStoragePath", method = RequestMethod.GET)
    public String getStoragePath() ;

    @RequestMapping(value = "/setStoragePath", method = RequestMethod.GET)
    public void setStoragePath(@RequestParam("storagePath") String storagePath) ;

    @RequestMapping(value = "/getMainUri", method = RequestMethod.GET)
    public String getMainUri() ;

    @RequestMapping(value = "/getMainUriStripped", method = RequestMethod.GET)
    public String getMainUriStripped() ;

    @RequestMapping(value = "/setMainUri", method = RequestMethod.GET)
    public void setMainUri(@RequestParam("mainUri") String mainUri) ;

    @RequestMapping(value = "/getTelegramMasterChatId", method = RequestMethod.GET)
    public String getTelegramMasterChatId() ;

    @RequestMapping(value = "/setTelegramMasterChatId", method = RequestMethod.GET)
    public void setTelegramMasterChatId(@RequestParam("id") String id) ;

    @RequestMapping(value = "/getOgLocales", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getOgLocales() ;

    @RequestMapping(value = "/getOgTypes", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getOgTypes();

}
