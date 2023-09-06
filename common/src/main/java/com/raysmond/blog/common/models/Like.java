package com.raysmond.blog.common.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "posts_likes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Like extends BaseModel {

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    @Column(nullable = false)
    private Integer sympathy;

    @Column(nullable = false)
    private String clientIp;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isAdmin;

    public void init() {
        this.setId(1L);
        this.setCreatedAt(new Date());
        this.setUpdatedAt(new Date());
    }
}
