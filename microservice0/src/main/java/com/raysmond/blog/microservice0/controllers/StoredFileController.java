package com.raysmond.blog.microservice0.controllers;

import com.raysmond.blog.microservice0.models.StoredFile;
import com.raysmond.blog.microservice0.services.FileStorageService;
import com.raysmond.blog.microservice0.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;


@Controller
@RequestMapping("/files")
public class StoredFileController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService storageService;

    @GetMapping(value = "/{fileName:.+}"/*, produces = APPLICATION_OCTET_STREAM_VALUE*/)
    @ExceptionHandler(value = FileNotFoundException.class)
    public @ResponseBody
    HttpEntity<byte[]> getFileById(@PathVariable String fileName, final HttpServletResponse response) throws IOException {

        StoredFile file = null;
        try {
            file = this.storageService.getFileByName(fileName);
        } finally {

        }

        if (file == null) {
            response.sendError(404, String.format("File %s not found", fileName));
            return null;
        }
        byte[] content;
//        try {
//            content = this.storageService.getFileContent(file.getPath());
//        } catch (IOException e) {
//            e.printStackTrace();
//            if (this.userService.isCurrentUserAdmin()) {
//                response.sendError(404, String.format("File %s (%s) not found", file.getName(), file.getPath()));
//            } else {
//                response.sendError(404, String.format("File %s not found", file.getName()));
//            }
            return null;
//        }

//        HttpHeaders header = new HttpHeaders();
//        //header.setContentType(APPLICATION_OCTET_STREAM);
//        header.set("Content-Type", HttpContentTypeSerializer.getContentType(file.getName()));
//        header.set("Content-Disposition", "inline; filename=" + file.getName());
//        header.setContentLength(file.getSize());
//
//        return new HttpEntity<byte[]>(content, header);
    }

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) throws IOException {

        MockHttpServletResponse response = new MockHttpServletResponse();

        if (method.equals("all") || method.equals("getFileById")) {
            getFileById_test("file", response);
        }

        return "test";
    }

    HttpEntity<byte[]> getFileById_test(String fileName, final HttpServletResponse response) throws IOException {

//        StoredFile file = this.storageService.getFileByName(fileName);
        StoredFile file = new StoredFile();
        if (file == null) {
            response.sendError(404, String.format("File %s not found", fileName));
            return null;
        }
        byte[] content;
//        try {
//            content = this.storageService.getFileContent(file.getPath());
//        } catch (IOException e) {
//            e.printStackTrace();
//            if (this.userService.isCurrentUserAdmin()) {
//                response.sendError(404, String.format("File %s (%s) not found", file.getName(), file.getPath()));
//            } else {
//                response.sendError(404, String.format("File %s not found", file.getName()));
//            }
        return null;
//        }
//
//        HttpHeaders header = new HttpHeaders();
//        //header.setContentType(APPLICATION_OCTET_STREAM);
//        header.set("Content-Type", HttpContentTypeSerializer.getContentType(file.getName()));
//        header.set("Content-Disposition", "inline; filename=" + file.getName());
//        header.setContentLength(file.getSize());
//
//        return new HttpEntity<byte[]>(content, header);
    }


}
