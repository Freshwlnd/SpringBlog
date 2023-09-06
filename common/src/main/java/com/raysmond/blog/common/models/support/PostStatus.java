package com.raysmond.blog.common.models.support;

import lombok.NoArgsConstructor;

/**
 * @author Raysmond<i@raysmond.com>
 */
public enum PostStatus {
    DRAFT("DRAFT"),
    PUBLISHED("PUBLISHED");

    private String name;

//    PostStatus(){
//        this.name = "Published";
//    }

    PostStatus(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId(){
        return name();
    }

    @Override
    public String toString() {
        return getName();
    }
}
