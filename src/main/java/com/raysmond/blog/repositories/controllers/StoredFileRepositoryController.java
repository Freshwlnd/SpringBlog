package com.raysmond.blog.repositories.controllers;

import com.raysmond.blog.models.StoredFile;
import com.raysmond.blog.repositories.StoredFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/StoredFileRepositoryController")
public class StoredFileRepositoryController {

    @Autowired
    StoredFileRepository storedFileRepository;

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        StoredFile storedFile = new StoredFile();
        Long fileId = 1L;
        String fileName = "fileName";

        switch (method) {
            case "all":
                for (int i = 0; i < 1; i++) {
                    delete(storedFile);
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
                delete(storedFile);
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

    void delete(StoredFile storedFile) {
        try {
            storedFileRepository.delete(storedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<StoredFile> findAll() {
        return storedFileRepository.findAll();
    }

    StoredFile findById(Long id) {
        return storedFileRepository.findById(id);
    }

    StoredFile findByName(String name) {
        return storedFileRepository.findByName(name);
    }

    StoredFile save(StoredFile storedFile) {
        return storedFileRepository.save(storedFile);
    }

}
