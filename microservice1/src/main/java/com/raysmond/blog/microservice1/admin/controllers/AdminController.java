package com.raysmond.blog.microservice1.admin.controllers;

import com.raysmond.blog.microservice1.client.DTOUtilClient;
import com.raysmond.blog.common.forms.SettingsForm;
import com.raysmond.blog.microservice1.services.AppSetting;
import com.raysmond.blog.microservice1.services.PostService;
import com.raysmond.blog.microservice1.support.web.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private DTOUtilClient DTOUtil;

    @Autowired
    private AppSetting appSetting;

    @Autowired
    private PostService postService;


    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("posts", postService.getPostsIdTitleList());
        return "admin/home/index";
    }

    @RequestMapping(value = "settings")
    public String settings(Model model){
        SettingsForm settingsForm = DTOUtil.map(appSetting, SettingsForm.class);

        model.addAttribute("settings", settingsForm);
        return "admin/home/settings";
    }

    @RequestMapping(value = "settings", method = RequestMethod.POST)
    public String updateSettings(@Valid SettingsForm settingsForm, Errors errors, Model model, RedirectAttributes ra){
        if (errors.hasErrors()){
            return "admin/settings";
        } else {
            appSetting.setSiteName(settingsForm.getSiteName());
            appSetting.setSiteSlogan(settingsForm.getSiteSlogan());
            appSetting.setPageSize(settingsForm.getPageSize());
            appSetting.setStoragePath(settingsForm.getStoragePath());
            appSetting.setMainUri(settingsForm.getMainUri());
            appSetting.setTelegramMasterChatId(settingsForm.getTelegramMasterChatId());

            MessageHelper.addSuccessAttribute(ra, "Update settings successfully.");

            return "redirect:settings";
        }
    }
}
