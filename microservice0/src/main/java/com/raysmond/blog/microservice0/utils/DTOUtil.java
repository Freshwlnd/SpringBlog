package com.raysmond.blog.microservice0.utils;

import com.raysmond.blog.microservice0.forms.PostForm;
import com.raysmond.blog.microservice0.models.Post;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raysmond<i @ raysmond.com>
 */
@Component
@RequestMapping("/DTOUtil")
public class DTOUtil {

    private static ModelMapper MAPPER = null;

    private static ModelMapper getMapper() {
        if (MAPPER == null) {
            MAPPER = new ModelMapper();
            MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        }

        return MAPPER;
    }

    public static <S, T> T map(S source, Class<T> targetClass) {
        return getMapper().map(source, targetClass);
    }

    public static <S, T> void mapTo(S source, T dist) {
        getMapper().map(source, dist);
    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        List<T> list = new ArrayList<>();
        for (S s : source) {
            list.add(getMapper().map(s, targetClass));
        }
        return list;
    }


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Post post = new Post();
        PostForm postForm = new PostForm();

        switch (method) {
            case "all":
                for (int i = 0; i < 7; i++) {
                    map_test(new Post(), PostForm.class);
                }
                for (int i = 0; i < 4; i++) {
                    mapTo_test(post, postForm);
                }
                break;
            case "map":
                map_test(new Post(), PostForm.class);
                break;
            case "mapTo":
                mapTo_test(post, postForm);
                break;
        }

        return "test";
    }

    public static <S, T> T map_test(S source, Class<T> targetClass) {
        return getMapper().map(source, targetClass);
    }

    public static <S, T> void mapTo_test(S source, T dist) {
        getMapper().map(source, dist);
    }


}
