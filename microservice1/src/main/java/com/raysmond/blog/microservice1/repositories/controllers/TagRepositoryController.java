package com.raysmond.blog.microservice1.repositories.controllers;

import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.Tag;
import com.raysmond.blog.microservice1.repositories.TagRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    void delete(@RequestBody Tag tag) {
        tagRepository.delete(tag);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @RequestMapping(value = "/findByName", method = RequestMethod.POST)
    @ResponseBody
    Tag findByName(@RequestParam("name") String name) {
        return tagRepository.findByName(name);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    Tag save(@RequestBody Tag tag) {
        try {
            return tagRepository.save(tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tag;
    }

}
