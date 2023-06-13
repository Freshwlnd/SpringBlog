package com.raysmond.blog.forms;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author Raysmond<i @ raysmond.com>
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

    // TODO
    public SettingsForm() {
        siteName = "SpringBlog";
        siteSlogan = "An interesting place to discover";
        pageSize = 5;
        storagePath = "/tmp";
        mainUri = "http://localhost/";
        telegramMasterChatId = "";
    }
}
