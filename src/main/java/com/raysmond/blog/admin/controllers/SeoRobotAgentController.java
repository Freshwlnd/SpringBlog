package com.raysmond.blog.admin.controllers;

import com.raysmond.blog.forms.PostForm;
import com.raysmond.blog.forms.SeoRobotAgentForm;
import com.raysmond.blog.models.SeoRobotAgent;
import com.raysmond.blog.models.User;
import com.raysmond.blog.repositories.SeoRobotAgentRepository;
import com.raysmond.blog.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/admin/robotsAgents")
public class SeoRobotAgentController {

    @Autowired
    private SeoRobotAgentRepository seoRobotAgentRepository;

    @GetMapping()
    public String getSeoRobotsAgents(Model model) {
        model.addAttribute("records", this.seoRobotAgentRepository.findAll());
        model.addAttribute("form", new SeoRobotAgentForm());
        return "admin/robotsAgents/index";
    }


    @GetMapping(value = "/{recordId:[\\d]+}/edit")
    public String editSeoRobotAgent(@PathVariable Long recordId, Model model) {

        SeoRobotAgent ua = this.seoRobotAgentRepository.findOne(recordId);

        Assert.notNull(ua);

        model.addAttribute("form", DTOUtil.map(ua, SeoRobotAgentForm.class));

        return "admin/robotsAgents/edit";
    }

    @PostMapping(value = "/{recordId:[\\d]+}/edit")
    public String saveSeoRobotAgent(@PathVariable Long recordId, @Valid SeoRobotAgentForm form, Errors errors) {
        SeoRobotAgent ua = null;
        if (recordId.equals(0L)) {
            ua = new SeoRobotAgent();
        } else {
            ua = this.seoRobotAgentRepository.findOne(recordId);
        }
        Assert.notNull(ua);

        DTOUtil.mapTo(form, ua);

        this.seoRobotAgentRepository.save(ua);

        return "redirect:/admin/robotsAgents";
    }


    @PostMapping(value = "/{recordId:[\\d]+}/delete")
    public String deleteSeoRobotAgent(@PathVariable Long recordId) {
        this.seoRobotAgentRepository.delete(recordId);
        return "redirect:/admin/robotsAgents";
    }

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Model model = new BindingAwareModelMap();
        SeoRobotAgentForm seoRobotAgentForm = new SeoRobotAgentForm();
        PostForm postForm = new PostForm();
        Errors errors = new BeanPropertyBindingResult(postForm,"postForm",true,256);

        if (method.equals("all") || method.equals("getSeoRobotsAgents")) {
            getSeoRobotsAgents_test(model);
        }
        if (method.equals("all") || method.equals("editSeoRobotAgent")) {
            editSeoRobotAgent_test(0L, model);
        }
        if (method.equals("all") || method.equals("saveSeoRobotAgent")) {
            saveSeoRobotAgent_test(0L, seoRobotAgentForm, errors);
        }
        if (method.equals("all") || method.equals("deleteSeoRobotAgent")) {
            deleteSeoRobotAgent_test(0L);
        }

        return "test";
    }

    public String getSeoRobotsAgents_test(Model model) {
//        model.addAttribute("records", this.seoRobotAgentRepository.findAll());
        model.addAttribute("records", new ArrayList<>());
        model.addAttribute("form", new SeoRobotAgentForm());
        return "admin/robotsAgents/index";
    }

    public String editSeoRobotAgent_test(Long recordId, Model model) {

//        SeoRobotAgent ua = this.seoRobotAgentRepository.findOne(recordId);
        SeoRobotAgent ua = new SeoRobotAgent();

//        Assert.notNull(ua);

//        model.addAttribute("form", DTOUtil.map(ua, SeoRobotAgentForm.class));
        model.addAttribute("form", ua);

        return "admin/robotsAgents/edit";
    }

    public String saveSeoRobotAgent_test(Long recordId, SeoRobotAgentForm form, Errors errors) {
        SeoRobotAgent ua = null;
        if (recordId.equals(0L)) {
            ua = new SeoRobotAgent();
        } else {
//            ua = this.seoRobotAgentRepository.findOne(recordId);
            ua = new SeoRobotAgent();
        }
        Assert.notNull(ua);

//        DTOUtil.mapTo(form, ua);

//        this.seoRobotAgentRepository.save(ua);

        return "redirect:/admin/robotsAgents";
    }

    public String deleteSeoRobotAgent_test(Long recordId) {
//        this.seoRobotAgentRepository.delete(recordId);
        return "redirect:/admin/robotsAgents";
    }

}
