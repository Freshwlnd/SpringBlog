package com.raysmond.blog.microservice0.repositories.controllers;

import com.raysmond.blog.microservice0.models.Tag;
import com.raysmond.blog.microservice0.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Raysmond<i @ raysmond.com>.
 */
@Controller
@RequestMapping("/TagRepositoryController")
public class TagRepositoryController {

    @Autowired
    TagRepository tagRepository;

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        String name = "name";
        Tag tag = new Tag();


        switch (method) {
            case "all":
                for (int i = 0; i < 3; i++) {
                    findByName(name);
                }
                for (int i = 0; i < 2; i++) {
                    save(tag);
                }
                break;
            case "findByName":
                findByName(name);
                break;
            case "save":
                save(tag);
                break;
        }

        return "test";
    }


    Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    Tag save(Tag tag) {
        try {
            return tagRepository.save(tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tag;
    }

}
