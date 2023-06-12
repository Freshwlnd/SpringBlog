package com.raysmond.blog.microservice0.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "seo_robots_agents")
@Getter
@Setter
public class SeoRobotAgent extends BaseModel {

    @Column(nullable = false)
    private String userAgent;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isRegexp;

    public SeoRobotAgent() {
        userAgent = "userAgent";
        isRegexp = false;
        setId(0L);
    }

    public SeoRobotAgent(String userAgent) {
        this.userAgent = userAgent;
    }

}
