package com.raysmond.blog.common.models;

import com.raysmond.blog.common.models.support.PostType;
import com.raysmond.blog.common.models.support.PostStatus;
import org.springframework.data.domain.Pageable;
import lombok.Data;

// TODO
@Data
public class PostParams {
    private PostType postType;
    private PostStatus postStatus;
    private Pageable pageRequest;
    private Boolean deleted;

    public PostParams(PostType postType, PostStatus postStatus, Pageable pageRequest, Boolean deleted) {
        this.postType = postType;
        this.postStatus = postStatus;
        this.pageRequest = pageRequest;
        this.deleted = deleted;
    }

    public PostParams(PostType postType, PostStatus postStatus, Boolean deleted) {
        this.postType = postType;
        this.postStatus = postStatus;
        this.pageRequest = null;
        this.deleted = deleted;
    }

}
