package com.raysmond.blog.microservice3.forms;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LikeForm {

    @NotNull
    private String sympathy = "0";

}
