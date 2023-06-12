package com.raysmond.blog.microservice0.admin.controllers;

import com.raysmond.blog.microservice0.forms.SettingsForm;
import com.raysmond.blog.microservice0.models.dto.PostIdTitleDTO;
import com.raysmond.blog.microservice0.services.AppSetting;
import com.raysmond.blog.microservice0.services.PostService;
import com.raysmond.blog.microservice0.support.web.MessageHelper;
import com.raysmond.blog.microservice0.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Raysmond<i @ raysmond.com>
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AppSetting appSetting;

    @Autowired
    private PostService postService;


    @RequestMapping("")
    public String index(Model model) {
        List<PostIdTitleDTO> postIdTitleDTOList = postService.getPostsIdTitleList();

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        Boolean bl = false;
//        Integer it = 1;
//        Long ln = 1L;
//        String str = "admin/home/index";
//        int itn = 1;
//        System.out.println("Boolean: ");
//        System.out.println(RamUsageEstimator.sizeOf(bl));
//        System.out.println("Integer: ");
//        System.out.println(RamUsageEstimator.sizeOf(it));
//        System.out.println("Long: ");
//        System.out.println(RamUsageEstimator.sizeOf(ln));
//        System.out.println("String: ");
//        System.out.println(RamUsageEstimator.sizeOf(str));
//        System.out.println("int: ");
//        System.out.println(RamUsageEstimator.sizeOf(itn));
//        System.out.println(RamUsageEstimator.sizeOf(postIdTitleDTOList));

        model.addAttribute("posts", postIdTitleDTOList);
        return "admin/home/index";
    }

    @RequestMapping(value = "settings")
    public String settings(Model model) {
        SettingsForm settingsForm = DTOUtil.map(appSetting, SettingsForm.class);

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println("com.raysmond.blog.services.AppSetting");
//        System.out.println(RamUsageEstimator.sizeOf(appSetting));
//        System.out.println(RamUsageEstimator.shallowSizeOf(appSetting));
//        System.out.println(ClassLayout.parseInstance(appSetting).toPrintable());
//        System.out.println("SettingsForm");
//        System.out.println(RamUsageEstimator.sizeOf(settingsForm));

        model.addAttribute("settings", settingsForm);
        return "admin/home/settings";
    }

    @RequestMapping(value = "settings", method = RequestMethod.POST)
    public String updateSettings(@Valid SettingsForm settingsForm, Errors errors, Model model, RedirectAttributes ra) {
        if (errors.hasErrors()) {
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

    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Model model = new BindingAwareModelMap();
        SettingsForm settingsForm = new SettingsForm();
        Errors errors = new BeanPropertyBindingResult(settingsForm, "settingsForm", true, 256);
        RedirectAttributes ra = new RedirectAttributesModelMap();

        if (method.equals("all") || method.equals("index")) {
            index_test(model);
        }
        if (method.equals("all") || method.equals("settings")) {
            settings_test(model, settingsForm);
        }
        if (method.equals("all") || method.equals("updateSettings")) {
            updateSettings_test(settingsForm, errors, model, ra);
        }
        return "test";
    }

    public String index_test(Model model) {
        List<PostIdTitleDTO> postIdTitleDTOList = new ArrayList<>();
        model.addAttribute("posts", postIdTitleDTOList);
        return "admin/home/index";
    }

    public String settings_test(Model model, SettingsForm settingsForm) {
//        SettingsForm settingsForm = DTOUtil.map(appSetting, SettingsForm.class);

        model.addAttribute("settings", settingsForm);
        return "admin/home/settings";
    }

    public String updateSettings_test(@Valid SettingsForm settingsForm, Errors errors, Model model, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return "admin/settings";
        } else {
//            appSetting.setSiteName(settingsForm.getSiteName());
//            appSetting.setSiteSlogan(settingsForm.getSiteSlogan());
//            appSetting.setPageSize(settingsForm.getPageSize());
//            appSetting.setStoragePath(settingsForm.getStoragePath());
//            appSetting.setMainUri(settingsForm.getMainUri());
//            appSetting.setTelegramMasterChatId(settingsForm.getTelegramMasterChatId());
//
            MessageHelper.addSuccessAttribute(ra, "Update settings successfully.");

            return "redirect:settings";
        }
    }

}
