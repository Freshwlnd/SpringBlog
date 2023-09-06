package com.raysmond.blog.common.models.support;

public enum OgLocale {

    en_EN("en_EN"),
    ru_RU("ru_RU");

    private String name;

//    OgLocale(){
//        this.name = "en_EN";
//    }

    OgLocale(String name){
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
