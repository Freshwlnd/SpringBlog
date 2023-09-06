package com.raysmond.blog.microservice1.repositories.controllers;

import com.raysmond.blog.common.models.Setting;
import com.raysmond.blog.microservice1.repositories.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Raysmond<i @ raysmond.com>
 */
@Controller
@RequestMapping("/SettingRepositoryController")
public class SettingRepositoryController {

    @Autowired
    SettingRepository settingRepository;

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Setting setting = null;

    private void init() {
        try {
            setting = settingRepository.findAll().get(0);
        } catch (Exception e) {
            setting = new Setting();
            setting = settingRepository.save(setting);
        }
        if (setting == null) {
            setting = new Setting();
            setting = settingRepository.save(setting);
        }
    }

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        String key = "key";
        if (setting == null) {
            init();
        }

        switch (method) {
            case "all":
                for (int i = 0; i < 13; i++) {
                    findByKey(key);
                }
                for (int i = 0; i < 6; i++) {
                    save(setting);
                }
                break;
            case "findByKey":
                findByKey(key);
                break;
            case "save":
                save(setting);
                break;
        }

        return "test";
    }


    @RequestMapping(value = "/findByKey", method = RequestMethod.GET)
    @ResponseBody
    Setting findByKey(@RequestParam("key") String key) {
        return settingRepository.findByKey(key);
//        Setting ret = settingRepository.findByKey(key);
//        return ret;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    Setting save(@RequestBody Setting setting) {
        try {
            return settingRepository.save(setting);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return setting;
    }
}
