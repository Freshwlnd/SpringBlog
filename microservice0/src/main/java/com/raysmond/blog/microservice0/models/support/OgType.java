package com.raysmond.blog.microservice0.models.support;

public enum OgType {

    ARTICLE("article");

    private String name;

    OgType(String name) {
        this.name = name;
    }

    public String getName() {
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
