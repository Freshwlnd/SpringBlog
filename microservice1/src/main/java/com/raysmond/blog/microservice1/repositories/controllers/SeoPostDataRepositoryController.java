package com.raysmond.blog.microservice1.repositories.controllers;

import com.raysmond.blog.common.models.SeoPostData;
import com.raysmond.blog.microservice1.repositories.SeoPostDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/SeoPostDataRepositoryController")
public class SeoPostDataRepositoryController {

    @Autowired
    SeoPostDataRepository seoPostDataRepository;

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    SeoPostData data = null;

    private void init() {
        try {
            data = seoPostDataRepository.findAll().get(0);
        } catch (Exception e) {
            data = new SeoPostData();
            data = seoPostDataRepository.save(data);
        }
        if (data == null) {
            data = new SeoPostData();
            data = seoPostDataRepository.save(data);
        }
    }

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        if (data == null) {
            init();
        }

        switch (method) {
            case "all":
                for (int i = 0; i < 5; i++) {
                    save(data);
                }
                break;
            case "save":
                save(data);
                break;
        }

        return "test";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    void save(@RequestBody SeoPostData data) {
        seoPostDataRepository.save(data);
    }

}
