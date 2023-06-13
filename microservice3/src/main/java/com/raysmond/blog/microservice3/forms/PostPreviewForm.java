package com.raysmond.blog.microservice3.forms;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by bvn13 on 28.01.2018.
 */
@Data
public class PostPreviewForm {

    @NotEmpty
    private String content;

}
