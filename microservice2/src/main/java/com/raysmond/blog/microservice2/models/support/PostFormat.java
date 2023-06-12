package com.raysmond.blog.microservice2.models.support;

/**
 * @author Raysmond<i @ raysmond.com>
 */
public enum PostFormat {
    HTML("Html"),
    MARKDOWN("Markdown");

    private String name;

    PostFormat(String name) {
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