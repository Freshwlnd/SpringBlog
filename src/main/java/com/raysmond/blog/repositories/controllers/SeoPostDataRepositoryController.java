package com.raysmond.blog.repositories.controllers;

import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.SeoPostData;
import com.raysmond.blog.repositories.SeoPostDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
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

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        SeoPostData data = new SeoPostData();

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

    void save(SeoPostData data) {
        seoPostDataRepository.save(data);
    }

}
