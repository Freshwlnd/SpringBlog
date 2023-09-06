package com.raysmond.blog.common.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raysmond.blog.common.models.support.PostType;
import com.raysmond.blog.common.models.support.PostStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
// import com.raysmond.blog.common.models.PageRequest;
import org.springframework.data.domain.PageRequest;
import lombok.Data;
import org.springframework.data.domain.Sort;

import org.springframework.web.servlet.DispatcherServlet;

import java.util.ArrayList;
import java.util.List;

// TODO
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostParams {
    private String tag = "tag";
    private PostType postType = PostType.POST;
    private PostStatus postStatus = PostStatus.PUBLISHED;
    private boolean isNullPageRequest = true;
    private int page = 0;
    private int size = 5;
    private String sortDirection = "DESC";
    List<String> properties= new ArrayList<String>();
    private Boolean deleted=false;


    public PostParams(Pageable pageRequest) {
        setPageRequest(pageRequest);
    }

    public PostParams(String tag, Pageable pageRequest) {
        this.tag = tag;
        setPageRequest(pageRequest);
    }

    public PostParams(PostType postType, PostStatus postStatus, Pageable pageRequest, Boolean deleted) {
    // public PostParams(PostType postType, PostStatus postStatus, PageRequest pageRequest, Boolean deleted) {
        this.postType = postType;
        this.postStatus = postStatus;
        setPageRequest(pageRequest);
        this.deleted = deleted;
    }

    public PostParams(PostType postType, PostStatus postStatus, Boolean deleted) {
        this.postType = postType;
        this.postStatus = postStatus;
        setPageRequest(null);
        this.deleted = deleted;
    }

    public PostParams(Pageable pageRequest, Boolean deleted) {
        setPageRequest(pageRequest);
        this.deleted = deleted;
    }

    @JsonIgnore
    public void setPageRequest(Pageable pageRequest) {
        if(pageRequest==null) {
            this.isNullPageRequest = true;
        } else {
            this.isNullPageRequest = false;
            this.properties= new ArrayList<String>();
            this.page = pageRequest.getPageNumber();
            this.size = pageRequest.getPageSize();
            pageRequest.getSort().iterator().forEachRemaining(order -> {
                this.sortDirection = order.getDirection().toString();
                this.properties.add(order.getProperty());
            });
        }
    }

    @JsonIgnore
    public Pageable getPageRequest() {
        return new PageRequest(this.page, this.size, Sort.Direction.fromString(this.sortDirection), this.properties.toArray(new String[this.properties.size()]));
    }

}
