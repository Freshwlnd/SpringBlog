package com.raysmond.blog.common.models.support;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
//public class WebError implements Serializable {
public class WebError {

    private String field;

    private String errorMessage;

    public WebError(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
    }

}
