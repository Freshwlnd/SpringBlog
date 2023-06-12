package com.raysmond.blog.microservice1.forms;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StoredFileForm {

    @NotNull
    private String title;

    @NotNull
    private String name;

    @NotNull
    private String path;

    @NotNull
    private Long size;

}
