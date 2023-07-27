package com.raysmond.blog.microservice1.client;

import com.raysmond.blog.common.models.StoredFile;
import com.raysmond.blog.common.models.User;

import java.io.IOException;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "springblog-microservice4")
@RequestMapping("/FileStorageService")
public interface FileStorageServiceClient {

    @RequestMapping(value = "/getFileById", method = RequestMethod.GET)
    @ResponseBody
    public StoredFile getFileById(@RequestParam("id") Long id);

    @RequestMapping(value = "/getFileByName", method = RequestMethod.GET)
    @ResponseBody
    public StoredFile getFileByName(@RequestParam("fileName") String fileName);

    @RequestMapping(value = "/storeFile", method = RequestMethod.GET)
    public void storeFile(@RequestParam("filename") String filename, @RequestParam("content") byte[] content) throws IOException;

    @RequestMapping(value = "/getFileContentById", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getFileContentById(@RequestParam("fileId") Long fileId) throws IOException;

    @RequestMapping(value = "/getFileContent", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getFileContent(@RequestParam("fullname") String fullname) throws IOException;

    @RequestMapping(value = "/deleteFileById", method = RequestMethod.GET)
    public void deleteFileById(@RequestParam("fileId") Long fileId) throws IOException;

    @RequestMapping(value = "/getContentType", method = RequestMethod.GET)
    @ResponseBody
    public String getContentType(@RequestParam("fileName") String fileName);

}
