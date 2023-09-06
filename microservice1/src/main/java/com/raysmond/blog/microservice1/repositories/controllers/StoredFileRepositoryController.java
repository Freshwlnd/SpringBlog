package com.raysmond.blog.microservice1.repositories.controllers;

import com.raysmond.blog.common.models.StoredFile;
import com.raysmond.blog.microservice1.repositories.StoredFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/StoredFileRepositoryController")
public class StoredFileRepositoryController {

    @Autowired
    StoredFileRepository storedFileRepository;

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    StoredFile storedFile = null;
    StoredFile deleteStoredFile = new StoredFile();

    private void init() {
        try {
            storedFile = storedFileRepository.findAll().get(0);
        } catch (Exception e) {
            storedFile = new StoredFile();
            storedFile = storedFileRepository.save(storedFile);
        }
        if (storedFile == null) {
            storedFile = new StoredFile();
            storedFile = storedFileRepository.save(storedFile);
        }
    }

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Long fileId = 1L;
        String fileName = "fileName";
        if (storedFile == null) {
            init();
        }

        switch (method) {
            case "all":
                for (int i = 0; i < 1; i++) {
                    delete(deleteStoredFile);
                }
                for (int i = 0; i < 1; i++) {
                    findAll();
                }
                for (int i = 0; i < 4; i++) {
                    findById(fileId);
                }
                for (int i = 0; i < 1; i++) {
                    findByName(fileName);
                }
                for (int i = 0; i < 1; i++) {
                    save(storedFile);
                }
                break;
            case "delete":
                delete(deleteStoredFile);
                break;
            case "findAll":
                findAll();
                break;
            case "findById":
                findById(fileId);
                break;
            case "findByName":
                findByName(fileName);
                break;
            case "save":
                save(storedFile);
                break;
        }

        return "test";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    void delete(@RequestBody StoredFile storedFile) {
        try {
            storedFileRepository.delete(storedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    List<StoredFile> findAll() {
        return storedFileRepository.findAll();
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ResponseBody
    StoredFile findById(@RequestParam("id") Long id) {
        return storedFileRepository.findById(id);
    }

    @RequestMapping(value = "/findByName", method = RequestMethod.GET)
    @ResponseBody
    StoredFile findByName(@RequestParam("name") String name) {
        return storedFileRepository.findByName(name);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    StoredFile save(@RequestBody StoredFile storedFile) {
        return storedFileRepository.save(storedFile);
    }

    @RequestMapping(value = "/saveAndFlush", method = RequestMethod.POST)
    @ResponseBody
    StoredFile saveAndFlush(@RequestBody StoredFile storedFile) {
        return storedFileRepository.saveAndFlush(storedFile);
    }
}
