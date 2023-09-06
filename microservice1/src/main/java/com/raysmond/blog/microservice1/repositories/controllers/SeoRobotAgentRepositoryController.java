package com.raysmond.blog.microservice1.repositories.controllers;

import com.raysmond.blog.common.models.SeoRobotAgent;
import com.raysmond.blog.microservice1.repositories.SeoRobotAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/SeoRobotAgentRepositoryController")
public class SeoRobotAgentRepositoryController {

    @Autowired
    SeoRobotAgentRepository seoRobotAgentRepository;

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    SeoRobotAgent ua = null;
    SeoRobotAgent deleteUa = new SeoRobotAgent();

    private void init() {
        try {
            ua = seoRobotAgentRepository.findAll().get(0);
        } catch (Exception e) {
            ua = new SeoRobotAgent();
            ua = seoRobotAgentRepository.save(ua);
        }
        if (ua == null) {
            ua = new SeoRobotAgent();
            ua = seoRobotAgentRepository.save(ua);
        }
    }

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Long recordId = 1L;
        if (ua == null) {
            init();
        }

        switch (method) {
            case "all":
                for (int i = 0; i < 1; i++) {
                    delete(deleteUa);
                }
                for (int i = 0; i < 1; i++) {
                    findAll();
                }
                for (int i = 0; i < 2; i++) {
                    findOne(recordId);
                }
                for (int i = 0; i < 1; i++) {
                    save(ua);
                }
                break;
            case "delete":
                delete(deleteUa);
                break;
            case "findAll":
                findAll();
                break;
            case "findOne":
                findOne(recordId);
                break;
            case "save":
                save(ua);
                break;
        }

        return "test";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    void delete(@RequestBody SeoRobotAgent ua) {
        try {
            seoRobotAgentRepository.delete(ua);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    List<SeoRobotAgent> findAll() {
        return seoRobotAgentRepository.findAll();
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ResponseBody
    SeoRobotAgent findOne(@RequestParam("recordId") Long recordId) {
        return seoRobotAgentRepository.findOne(recordId);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    SeoRobotAgent save(@RequestBody SeoRobotAgent ua) {
        return seoRobotAgentRepository.save(ua);
    }

}
