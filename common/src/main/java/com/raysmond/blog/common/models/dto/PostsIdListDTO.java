package com.raysmond.blog.common.models.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bvn13 on 25.12.2017.
 */
@Data
public class PostsIdListDTO {
    private List<Integer> ids;

    public void init() {
        this.ids = new ArrayList<>();
        ids.add(1);
    }
}
