package com.raysmond.blog.common.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by bvn13 on 22.12.2017.
 */
@Data
@AllArgsConstructor
//public class AjaxAnswerDTO implements Serializable {
public class AjaxAnswerDTO {

    private Boolean error;
    private String message;

}
