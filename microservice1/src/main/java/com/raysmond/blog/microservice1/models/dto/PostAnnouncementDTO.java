package com.raysmond.blog.microservice1.models.dto;

/**
 * Created by bvn13 on 22.12.2017.
 */
public class PostAnnouncementDTO extends AjaxAnswerDTO {
    public PostAnnouncementDTO(Boolean error, String message) {
        super(error, message);
    }

    public PostAnnouncementDTO(Boolean error) {
        super(error, "");
    }
}
