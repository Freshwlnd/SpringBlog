package com.raysmond.blog.common.models.support;

/**
 * @author Raysmond<i@raysmond.com>
 */
public enum PostFormat {
    HTML("HTML"),
    MARKDOWN("MARKDOWN");

    private String name;

//    PostFormat(){
//        this.name = "Markdown";
//    }

    PostFormat(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String getId() {
        return name();
    }

    @Override
    public String toString() {
        return getName();
    }
}