package com.raysmond.blog.microservice3.repositories.controllers;

import com.raysmond.blog.microservice3.models.Setting;
import com.raysmond.blog.microservice3.repositories.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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


    Setting findByKey(String key) {
        return settingRepository.findByKey(key);
    }

    Setting save(Setting setting) {
        try {
            return settingRepository.save(setting);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return setting;
    }
}
