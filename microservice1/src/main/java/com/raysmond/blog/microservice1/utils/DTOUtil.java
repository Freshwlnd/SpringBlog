package com.raysmond.blog.microservice1.utils;

import com.raysmond.blog.common.forms.*;
import com.raysmond.blog.common.models.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Raysmond<i@raysmond.com>
 */
@RestController
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
//
//    @Nullable
//    @RequestMapping(value = "/map", method = RequestMethod.POST)
//    @ResponseBody
//    public static <S, T> T map(@RequestBody DTORequestParams<S, T> dtoRequestParams) {
//        S source = dtoRequestParams.getSource();
//        Class<T> targetClass = dtoRequestParams.getTargetClass();
//        return getMapper().map(source, targetClass);
//    }
//
//    @Nullable
//    @RequestMapping(value = "/mapTo", method = RequestMethod.POST)
//    public static <S, T> void mapTo(@RequestBody DTORequestParams<S, T> dtoRequestParams) {
//        S source = dtoRequestParams.getSource();
//        T dist = dtoRequestParams.getDist();
//        getMapper().map(source, dist);
//    }
//
//    @RequestMapping(value = "/mapList", method = RequestMethod.POST)
//    @ResponseBody
//    public static <S, T> List<T> mapList(@RequestBody DTORequestParams<S, T> dtoRequestParams) {
//        List<S> source = dtoRequestParams.getSources();
//        Class<T> targetClass = dtoRequestParams.getTargetClass();
//        List<T> list = new ArrayList<>();
//        for (S s : source) {
//            list.add(getMapper().map(s, targetClass));
//        }
//        return list;
//    }

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