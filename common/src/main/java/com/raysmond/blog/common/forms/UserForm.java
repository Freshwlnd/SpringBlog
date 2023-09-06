package com.raysmond.blog.common.forms;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Raysmond<i@raysmond.com>.
 */
@Data
public class UserForm {
    @NotNull
    private String password;

    @NotNull
    private String newPassword;

    public void init() {
        this.password = "dec6ea6a577362d29821679ce658abf122fcc9507a406cdd0afdc977e4ff71f7e10f3f931837eb38";
        this.newPassword = "dec6ea6a577362d29821679ce658abf122fcc9507a406cdd0afdc977e4ff71f7e10f3f931837eb38";
    }
}
