package com.raysmond.blog.common.models.support;

/**
 * @author Raysmond<i@raysmond.com>
 */
public enum PostType {
    PAGE("PAGE"),
    POST("POST");

    private String name;

    PostType(){
        this.name = "POST";
    }

    PostType(String name){
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
