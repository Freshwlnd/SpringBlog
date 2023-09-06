package com.raysmond.blog.microservice1.client;

import com.raysmond.blog.common.models.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
@FeignClient(name = "springblog-microservice6")
@RequestMapping("/AppSetting")
public interface AppSettingClient {

    @RequestMapping(value = "/getSiteName", method = RequestMethod.GET)
    @ResponseBody
    public String getSiteName() ;

    @RequestMapping(value = "/setSiteName", method = RequestMethod.GET)
    @ResponseBody
    public void setSiteName(@RequestParam("siteName") String siteName) ;

    @RequestMapping(value = "/getPageSize", method = RequestMethod.GET)
    @ResponseBody
    public Integer getPageSize() ;

    @RequestMapping(value = "/setPageSize", method = RequestMethod.GET)
    @ResponseBody
    public void setPageSize(@RequestParam("pageSize") Integer pageSize) ;

    @RequestMapping(value = "/getSiteSlogan", method = RequestMethod.GET)
    @ResponseBody
    public String getSiteSlogan() ;

    @RequestMapping(value = "/setSiteSlogan", method = RequestMethod.GET)
    @ResponseBody
    public void setSiteSlogan(@RequestParam("siteSlogan") String siteSlogan) ;

    @RequestMapping(value = "/getStoragePath", method = RequestMethod.GET)
    @ResponseBody
    public String getStoragePath() ;

    @RequestMapping(value = "/setStoragePath", method = RequestMethod.GET)
    @ResponseBody
    public void setStoragePath(@RequestParam("storagePath") String storagePath) ;

    @RequestMapping(value = "/getMainUri", method = RequestMethod.GET)
    @ResponseBody
    public String getMainUri() ;

    @RequestMapping(value = "/getMainUriStripped", method = RequestMethod.GET)
    @ResponseBody
    public String getMainUriStripped() ;

    @RequestMapping(value = "/setMainUri", method = RequestMethod.GET)
    @ResponseBody
    public void setMainUri(@RequestParam("mainUri") String mainUri) ;

    @RequestMapping(value = "/getTelegramMasterChatId", method = RequestMethod.GET)
    @ResponseBody
    public String getTelegramMasterChatId() ;

    @RequestMapping(value = "/setTelegramMasterChatId", method = RequestMethod.GET)
    @ResponseBody
    public void setTelegramMasterChatId(@RequestParam("id") String id) ;

    @RequestMapping(value = "/getOgLocales", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getOgLocales() ;

    @RequestMapping(value = "/getOgTypes", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getOgTypes();

}
