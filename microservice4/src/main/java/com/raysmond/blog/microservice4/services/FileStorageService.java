package com.raysmond.blog.microservice4.services;


import com.raysmond.blog.common.models.User;
import com.raysmond.blog.microservice4.client.AppSettingClient;
import com.raysmond.blog.microservice4.client.StoredFileRepositoryClient;
import com.raysmond.blog.microservice4.client.UserServiceClient;
import com.raysmond.blog.microservice4.error.NotFoundException;
import com.raysmond.blog.common.models.StoredFile;
import com.raysmond.blog.microservice4.support.web.HttpContentTypeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequestMapping("/FileStorageService")
public class FileStorageService {

    public static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    private UserServiceClient userService;

    private AppSettingClient appSetting;

    private StoredFileRepositoryClient repository;

    @Autowired
    public FileStorageService(StoredFileRepositoryClient repository, AppSettingClient appSetting) {
        this.repository = repository;
        this.appSetting = appSetting;
//        logger.debug("== UPLOAD PATH == > " + appSetting.getStoragePath());
    }

    @RequestMapping(value = "/getFileById", method = RequestMethod.GET)
    @ResponseBody
    public StoredFile getFileById(@RequestParam("id") Long id) {
        StoredFile storedFile = repository.findById(id);
        return repository.findById(id);
    }

    @RequestMapping(value = "/getFileByName", method = RequestMethod.GET)
    @ResponseBody
    public StoredFile getFileByName(@RequestParam("fileName") String fileName) {
        StoredFile file = null;

        file = repository.findByName(fileName);
//        if (file == null) {
//            if (fileName.matches("\\d+")) {
        file = this.getFileById(Long.valueOf(fileName));
//            }
//        }

        if (file == null) {
            throw new NotFoundException("File " + fileName + " is not found");
        }

        return file;
    }

    @RequestMapping(value = "/storeFile", method = RequestMethod.GET)
    @ResponseBody
    public void storeFile(@RequestParam("filename") String filename, @RequestParam("content") byte[] content) throws IOException {
        File storage = new File(appSetting.getStoragePath());
        if (!storage.exists()) {
            storage.mkdirs();
        }
        String separator = "";
        if (!appSetting.getStoragePath().endsWith("//")) {
            separator = "//";
        }
        String fullname = appSetting.getStoragePath() + separator + filename;
        Path path = Paths.get(fullname);
        Files.write(path, content);

        File file = new File(fullname);

        StoredFile storedFile = new StoredFile();
        storedFile.setPath(path.toAbsolutePath().toString());
        storedFile.setUser(this.userService.currentUser());
        storedFile.setTitle(filename);
        storedFile.setName(filename);
        storedFile.setSize(file.length());

        // TODO
        storedFile.init();

        this.repository.saveAndFlush(storedFile);
    }

    @RequestMapping(value = "/getFileContentById", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getFileContentById(@RequestParam("fileId") Long fileId) throws IOException {
        StoredFile storedFile = this.repository.findById(fileId);
        Path path = Paths.get(storedFile.getPath());
        return Files.readAllBytes(path);
    }

    @RequestMapping(value = "/getFileContent", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getFileContent(@RequestParam("fullname") String fullname) throws IOException {
        Path path = Paths.get(fullname);
        return Files.readAllBytes(path);
    }

    @RequestMapping(value = "/deleteFileById", method = RequestMethod.GET)
    @ResponseBody
    public void deleteFileById(@RequestParam("fileId") Long fileId) throws IOException {
        StoredFile storedFile = this.repository.findById(fileId);
        Path path = null;
        try {
            path = Paths.get(storedFile.getPath());
            // first delete info, second delete file
            // because file might be deleted already
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        try {
            this.repository.delete(storedFile);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        try{
            Files.delete(path);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/getContentType", method = RequestMethod.GET)
    @ResponseBody
    public String getContentType(@RequestParam("fileName") String fileName) {
        return HttpContentTypeSerializer.getContentType(fileName);
    }



    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Long fileId = 1L;
        String filename = "filename";
        byte[] content = "content".getBytes();

        switch (method) {
            case "all":
                for (int i = 0; i < 1; i++) {
                    getFileById_test(fileId);
                }
                for (int i = 0; i < 2; i++) {
                    getFileByName_test(filename);
                }
                for (int i = 0; i < 1; i++) {
                    storeFile_test(filename, content);
                }
                for (int i = 0; i < 1; i++) {
                    deleteFileById_test(fileId);
                }
                break;
            case "getFileById":
                getFileById_test(fileId);
                break;
            case "getFileByName":
                getFileByName_test(filename);
                break;
            case "storeFile":
                storeFile_test(filename, content);
                break;
            case "deleteFileById":
                deleteFileById_test(fileId);
                break;
        }

        return "test";
    }

    public StoredFile getFileById_test(Long fileId) {
//        return repository.findById(id);
        return new StoredFile();
    }

    public StoredFile getFileByName_test(String filename) {
        StoredFile file = null;

//        file = repository.findByName(fileName);
//        if (file == null) {
//            if (fileName.matches("\\d+")) {
//        file = this.getFileById(Long.valueOf(fileName));
//            }
//        }

        if (file == null) {
//            throw new NotFoundException("File " + fileName + " is not found");
        }

        return file;
    }

    public void storeFile_test(String filename, byte[] content) {
//        File storage = new File(appSetting.getStoragePath());
        File storage = new File("/tmp");
        if (!storage.exists()) {
            storage.mkdirs();
        }
        String separator = "";
//        if (!appSetting.getStoragePath().endsWith("//")) {
        separator = "//";
//        }
//        String fullname = appSetting.getStoragePath() + separator + filename;
        String fullname = "/tmp" + separator + filename;
        Path path = Paths.get(fullname);
        try {
            Files.write(path, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(fullname);

        StoredFile storedFile = new StoredFile();
        storedFile.setPath(path.toAbsolutePath().toString());
//        storedFile.setUser(this.userService.currentUser());
        storedFile.setUser(new User());
        storedFile.setTitle(filename);
        storedFile.setName(filename);
        storedFile.setSize(file.length());

//        this.repository.saveAndFlush(storedFile);
    }

    public void deleteFileById_test(Long fileId) {
//        StoredFile storedFile = this.repository.findById(fileId);
        StoredFile storedFile = new StoredFile();
        Path path = Paths.get(storedFile.getPath());
        // first delete info, second delete file
        // because file might be deleted already
//        this.repository.delete(storedFile);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
