package com.raysmond.blog.microservice2.utils;

import com.raysmond.blog.common.models.DTORequestParams;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Raysmond<i@raysmond.com>
 */
@RestController
@RequestMapping("/DTOUtil")
public class DTOUtil {

    private static ModelMapper MAPPER = null;

    private static ModelMapper getMapper(){
        if(MAPPER == null){
            MAPPER = new ModelMapper();
            MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        }

        return MAPPER;
    }

    // TODO
    @RequestMapping(value = "/map", method = POST)
    public static <S, T> T map(@RequestBody DTORequestParams<S, T> dtoRequestParams) {
        return getMapper().map(dtoRequestParams.getSource(), dtoRequestParams.getTargetClass());
    }
//    public static <S, T> T map(@RequestBody S source, @RequestParam("targetClass") Class<T> targetClass) {
//        return getMapper().map(source, targetClass);
//    }

    // TODO
    @RequestMapping(value = "/mapTo", method = POST)
    public static <S, T> void mapTo(@RequestBody DTORequestParams<S, T> dtoRequestParams) {
        getMapper().map(dtoRequestParams.getSource(), dtoRequestParams.getDist());
    }
//    public static <S, T> void mapTo(@RequestBody S source, @RequestParam("dist") T dist) {
//        getMapper().map(source, dist);
//    }

    // TODO
    @RequestMapping(value = "/mapList", method = POST)
    public static <S, T> List<T> mapList(@RequestBody DTORequestParams<S, T> dtoRequestParams) {
        List<T> list = new ArrayList<>();
        for (S s : dtoRequestParams.getSources()) {
            list.add(getMapper().map(s, dtoRequestParams.getTargetClass()));
        }
        return list;
    }
//    public static <S, T> List<T> mapList(@RequestBody List<S> sources, @RequestParam("targetClass") Class<T> targetClass) {
//        List<T> list = new ArrayList<>();
//        for (S s : source) {
//            list.add(getMapper().map(s, targetClass));
//        }
//        return list;
//    }

    @RequestMapping(value = "/test", method = GET)
    public static String test() {
        String str = "hello";
        return str;
    }

}
