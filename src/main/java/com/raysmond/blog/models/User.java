package com.raysmond.blog.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Entity
@Table(name = "users")
@Getter @Setter
public class User extends BaseModel {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER  = "ROLE_USER";

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private String role = ROLE_USER;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private Collection<Post> posts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private Collection<StoredFile> storedFiles = new ArrayList<>();

    // TODO
    public User() {
        this.email = "admin@admin.com";
        this.password = "0f89fd5f7e08cab4e12a383c4ce59e1881a1dc987ea80711d535435c56e890e16826514cc6cdcb83";
        this.role = ROLE_ADMIN;
    }

    public Boolean isAdmin() {
        return this.role.equals(ROLE_ADMIN);
    }

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
