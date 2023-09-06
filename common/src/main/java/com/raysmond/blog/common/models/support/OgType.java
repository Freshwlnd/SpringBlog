package com.raysmond.blog.common.models.support;

public enum OgType {

    ARTICLE("ARTICLE");

    private String name;

//    OgType(){
//        this.name = "article";
//    }

    OgType(String name){
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
