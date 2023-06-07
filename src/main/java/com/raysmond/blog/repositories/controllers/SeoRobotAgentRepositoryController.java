package com.raysmond.blog.repositories.controllers;

import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.SeoRobotAgent;
import com.raysmond.blog.models.User;
import com.raysmond.blog.models.Visit;
import com.raysmond.blog.repositories.SeoRobotAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/SeoRobotAgentRepositoryController")
public class SeoRobotAgentRepositoryController {

    @Autowired
    SeoRobotAgentRepository seoRobotAgentRepository;

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    SeoRobotAgent ua = new SeoRobotAgent();

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Long recordId = 1L;

        switch (method) {
            case "all":
                for (int i = 0; i < 1; i++) {
                    delete(ua);
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
                delete(ua);
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


    void delete(SeoRobotAgent ua) {
        try {
            seoRobotAgentRepository.delete(ua);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<SeoRobotAgent> findAll() {
        return seoRobotAgentRepository.findAll();
    }

    SeoRobotAgent findOne(Long recordId) {
        return seoRobotAgentRepository.findOne(recordId);
    }

    SeoRobotAgent save(SeoRobotAgent ua) {
        return seoRobotAgentRepository.save(ua);
    }

}
