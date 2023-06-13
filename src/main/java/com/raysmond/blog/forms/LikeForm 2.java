package com.raysmond.blog.forms;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LikeForm {

    @NotNull
    private String sympathy = "0";

}
