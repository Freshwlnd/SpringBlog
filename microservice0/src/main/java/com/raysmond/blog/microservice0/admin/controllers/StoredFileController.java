package com.raysmond.blog.microservice0.admin.controllers;

import com.raysmond.blog.microservice0.error.NotFoundException;
import com.raysmond.blog.microservice0.forms.StoredFileForm;
import com.raysmond.blog.microservice0.models.StoredFile;
import com.raysmond.blog.microservice0.models.User;
import com.raysmond.blog.microservice0.repositories.StoredFileRepository;
import com.raysmond.blog.microservice0.services.FileStorageService;
import com.raysmond.blog.microservice0.services.UserService;
import com.raysmond.blog.microservice0.utils.DTOUtil;
import com.raysmond.blog.microservice0.utils.PaginatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller("adminUploadController")
@RequestMapping("admin/files")
public class StoredFileController {

    private static final int PAGE_SIZE = 20;

    @Autowired
    private FileStorageService storageService;

    @Autowired
    private StoredFileRepository storedFileRepository;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String index(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<StoredFile> files = storedFileRepository.findAll(new PageRequest(page, PAGE_SIZE, Sort.Direction.DESC, "id"));

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println("Page<StoredFile>");
//        System.out.println(RamUsageEstimator.sizeOf(files));
//        System.out.println(RamUsageEstimator.shallowSizeOf(files));
//        System.out.println(ClassLayout.parseInstance(files).toPrintable());

        model.addAttribute("totalPages", files.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("files", files);
        model.addAttribute("pagesList", PaginatorUtil.createPagesList(0, files.getTotalPages() - 1));

        return "admin/files/index";
    }

    @PostMapping("/upload") //new annotation since 4.3
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("uploadStatus", "Please select a file to upload");
            return "redirect:/admin/files/status";
        }

        String message = "";

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();

            // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//            System.out.println("byte: ");
//            System.out.println(RamUsageEstimator.sizeOf(bytes));
//            System.out.println("String: ");
//            System.out.println(RamUsageEstimator.sizeOf(file.getOriginalFilename()));


            this.storageService.storeFile(file.getOriginalFilename(), bytes);

            message = "You successfully uploaded '" + file.getOriginalFilename() + "'";
            redirectAttributes.addFlashAttribute("uploadStatus", message);

        } catch (IOException e) {
            e.printStackTrace();
            message = "Internal server error occured";
            redirectAttributes.addFlashAttribute("uploadStatus", message);
        }

        return "redirect:/admin/files/status";
    }

    @GetMapping("/status")
    public String uploadStatus() {
        return "admin/files/status";
    }



    @GetMapping(value = "/{fileId:[\\d]+}/edit")
    public String editFileById(@PathVariable Long fileId, Model model) {
        Assert.notNull(fileId);
        StoredFile file = this.storageService.getFileById(fileId);
        if (file == null) {
            //response.sendError(404, String.format("File %s not found", fileId));
            throw new NotFoundException(String.format("File with id %s not found", fileId));
        }

        StoredFileForm fileForm = DTOUtil.map(file, StoredFileForm.class);

        model.addAttribute("file", file);
        model.addAttribute("fileForm", fileForm);


        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println("StoredFileForm: ");
//        System.out.println(RamUsageEstimator.sizeOf(fileForm));
//        System.out.println("StoredFile: ");
//        System.out.println(RamUsageEstimator.sizeOf(file));
//        System.out.println(RamUsageEstimator.shallowSizeOf(file));
//        System.out.println(ClassLayout.parseInstance(file).toPrintable());

        return "admin/files/edit";
    }


    @PostMapping(value = "/{fileId:[\\d]+}")
    public String saveFile(@PathVariable Long fileId, @Valid StoredFileForm fileForm, Errors errors) {
        Assert.notNull(fileId);

        if (errors.hasErrors()) {
            return String.format("admin/files/%d/edit", fileId);
        }

        StoredFile storedFile = this.storedFileRepository.findById(fileId);
        DTOUtil.mapTo(fileForm, storedFile);
        storedFile.setUser(this.userService.currentUser());
        storedFile.setUpdatedAt(new Date());
        this.storedFileRepository.save(storedFile);

        return "redirect:/admin/files";
    }

    @RequestMapping(value = "{fileId:[0-9]+}/delete", method = {DELETE, POST})
    public String deletePost(@PathVariable Long fileId) {
        try {
            this.storageService.deleteFileById(fileId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/files";
    }

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Model model = new BindingAwareModelMap();
        byte[] content = "Hello World!".getBytes();
        MultipartFile file = new MockMultipartFile("file", "hello.txt", "text/plain", content);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        StoredFileForm fileForm = new StoredFileForm();
        Errors errors = new BeanPropertyBindingResult(fileForm, "fileForm", true, 256);

        if (method.equals("all") || method.equals("index")) {
            index_test(0, model);
        }
        if (method.equals("all") || method.equals("upload")) {
            upload_test(file, redirectAttributes);
        }
        if (method.equals("all") || method.equals("editFileById")) {
            editFileById_test(0L, model);
        }
        if (method.equals("all") || method.equals("saveFile")) {
            saveFile_test(0L, fileForm, errors);
        }
        if (method.equals("all") || method.equals("deletePost")) {
            deletePost_test(0L);
        }

        return "test";
    }

    public String index_test(int page, Model model) {
//        Page<StoredFile> files = storedFileRepository.findAll(new PageRequest(page, PAGE_SIZE, Sort.Direction.DESC, "id"));

        new PageRequest(page, PAGE_SIZE, Sort.Direction.DESC, "id");
        Page<StoredFile> files = new PageImpl<StoredFile>(new ArrayList<StoredFile>());

        model.addAttribute("totalPages", files.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("files", files);
        model.addAttribute("files", files);
//        model.addAttribute("pagesList", PaginatorUtil.createPagesList(0, files.getTotalPages()-1));

        return "admin/files/index";
    }

    public String upload_test(MultipartFile file, RedirectAttributes redirectAttributes) {
//        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("uploadStatus", "Please select a file to upload");
//            return "redirect:/admin/files/status";
//        }

        String message = "";

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();

            file.getOriginalFilename();
//            this.storageService.storeFile(file.getOriginalFilename(), bytes);

            message = "You successfully uploaded '" + file.getOriginalFilename() + "'";
            redirectAttributes.addFlashAttribute("uploadStatus", message);

        } catch (IOException e) {
            e.printStackTrace();
            message = "Internal server error occured";
            redirectAttributes.addFlashAttribute("uploadStatus", message);
        }

        return "redirect:/admin/files/status";
    }

    public String editFileById_test(Long fileId, Model model) {
        Assert.notNull(fileId);
//        StoredFile file = this.storageService.getFileById(fileId);
        StoredFile file = new StoredFile();
        if (file == null) {
            //response.sendError(404, String.format("File %s not found", fileId));
            throw new NotFoundException(String.format("File with id %s not found", fileId));
        }

//        StoredFileForm fileForm = DTOUtil.map(file, StoredFileForm.class);
        StoredFileForm fileForm = new StoredFileForm();

        model.addAttribute("file", file);
        model.addAttribute("fileForm", fileForm);

        return "admin/files/edit";
    }

    public String saveFile_test(Long fileId, StoredFileForm fileForm, Errors errors) {
        Assert.notNull(fileId);

        if (errors.hasErrors()) {
            return String.format("admin/files/%d/edit", fileId);
        }

//        StoredFile storedFile = this.storedFileRepository.findById(fileId);
        StoredFile storedFile = new StoredFile();
//        DTOUtil.mapTo(fileForm, storedFile);
//        storedFile.setUser(this.userService.currentUser());
        storedFile.setUser(new User());
        storedFile.setUpdatedAt(new Date());
//        this.storedFileRepository.save(storedFile);

        return "redirect:/admin/files";
    }

    public String deletePost_test(Long fileId) {
//        try {
//            this.storageService.deleteFileById(fileId);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return "redirect:/admin/files";
    }

}
