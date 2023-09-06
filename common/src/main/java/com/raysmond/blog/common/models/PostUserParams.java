package com.raysmond.blog.common.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO
@Data
@NoArgsConstructor
public class PostUserParams extends BaseModel {
    private Post post;
    private User user;

    public PostUserParams(Post post, User user) {
        this.post = post;
        this.user = user;
    }

}
