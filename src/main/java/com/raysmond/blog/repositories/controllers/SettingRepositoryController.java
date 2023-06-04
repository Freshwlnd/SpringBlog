package com.raysmond.blog.repositories.controllers;

import com.raysmond.blog.models.Setting;
import com.raysmond.blog.repositories.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Controller
@RequestMapping("/SettingRepositoryController")
public class SettingRepositoryController {

    @Autowired
    SettingRepository settingRepository;

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        String key = "key";
        Setting setting = new Setting();

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
        return settingRepository.save(setting);
    }
}
