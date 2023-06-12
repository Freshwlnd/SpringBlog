package com.raysmond.blog.microservice1.forms;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SeoRobotAgentForm {

    @NotNull
    private Long id = 0L;

    @NotNull
    private String userAgent = "";

    @NotNull
    private Boolean isRegexp = false;
}
