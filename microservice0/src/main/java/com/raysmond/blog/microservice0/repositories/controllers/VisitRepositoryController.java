package com.raysmond.blog.microservice0.repositories.controllers;

import com.raysmond.blog.microservice0.models.Post;
import com.raysmond.blog.microservice0.models.User;
import com.raysmond.blog.microservice0.models.Visit;
import com.raysmond.blog.microservice0.repositories.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/VisitRepositoryController")
public class VisitRepositoryController {

    @Autowired
    VisitRepository visitRepository;

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Visit visit = null;
    String clientIp = "clientIp";
    Post post = new Post();
    User user = new User();
    String userAgent = "userAgent";

    private void init() {
        try {
            visit = visitRepository.findAll().get(0);
        } catch (Exception e) {
            visit = new Visit();
            visit.setClientIp(clientIp);
            visit.setPost(post);
            visit.setUser(user);
            visit.setIsAdmin(user != null ? user.isAdmin() : false);
            visit.setUserAgent(userAgent);
        }
        if (visit == null) {
            visit = new Visit();
            visit.setClientIp(clientIp);
            visit.setPost(post);
            visit.setUser(user);
            visit.setIsAdmin(user != null ? user.isAdmin() : false);
            visit.setUserAgent(userAgent);
        }
    }

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        if (visit == null) {
            init();
        }


        if (method.equals("all") || method.equals("save")) {
            save(visit);
        }

        return "test";
    }

    Visit save(Visit visit) {
        return visitRepository.save(visit);
    }

}
