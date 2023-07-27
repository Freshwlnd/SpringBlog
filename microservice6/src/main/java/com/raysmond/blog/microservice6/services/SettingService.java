package com.raysmond.blog.microservice6.services;

import java.io.Serializable;

/**
 * @author Raysmond<i@raysmond.com>
 */
public interface SettingService {
//    Serializable get(String key);
//    Serializable get(String key, Serializable defaultValue);
//    void put(String key, Serializable value);
    // TODO
    String get(String key);
    String get(String key, String defaultValue);
    void put(String key, String value);
}
