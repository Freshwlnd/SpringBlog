package com.raysmond.blog.common.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

    }

    public SeoRobotAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void init() {
        this.userAgent = "userAgent";
        this.isRegexp = true;
        this.setId(1L);
        this.setCreatedAt(new Date());
        this.setUpdatedAt(new Date());
    }


}
