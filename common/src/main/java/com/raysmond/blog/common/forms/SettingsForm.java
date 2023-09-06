package com.raysmond.blog.common.forms;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Data
public class SettingsForm {

    @NotEmpty
    @NotNull
    private String siteName;

    @NotNull
    private String siteSlogan;

    @NotNull
    private Integer pageSize;

    @NotNull
    private String storagePath;

    @NotNull
    private String mainUri;

    @NotNull
    private String telegramMasterChatId;

    public void init() {
        this.siteName = "SpringBlog";
        this.siteSlogan = "An interesting place to discover";
        this.pageSize = 5;
        this.storagePath = "/tmp";
        this.mainUri = "http://localhost/";
        this.telegramMasterChatId = "";
    }

}
