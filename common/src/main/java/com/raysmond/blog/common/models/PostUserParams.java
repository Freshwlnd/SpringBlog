package com.raysmond.blog.common.models;

import lombok.Data;

// TODO
@Data
public class PostUserParams {
    private Post post;
    private User user;

    public PostUserParams(Post post, User user) {
        this.post = post;
        this.user = user;
    }

}
