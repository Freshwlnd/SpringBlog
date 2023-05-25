package com.raysmond.blog.common.models;

import lombok.Data;

import java.util.List;

// TODO
@Data
public class DTORequestParams<S, T> {
    private S source;
    private List<S> sources;
    private Class<T> targetClass;
    private T dist;

    public DTORequestParams(S source, List<S> sources, Class<T> targetClass, T dist) {
        this.source = source;
        this.sources = sources;
        this.targetClass = targetClass;
        this.dist = dist;
    }

}
