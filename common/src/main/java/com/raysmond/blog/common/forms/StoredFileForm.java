package com.raysmond.blog.common.forms;

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

    public void init() {
        this.setPath("/tmp/testSaveFile.txt");
        this.setTitle("testSaveFile");
        this.setName("testSaveFile");
        this.setSize(11L);
    }
}
