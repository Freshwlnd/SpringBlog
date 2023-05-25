package com.raysmond.blog.microservice1.client;

import com.raysmond.blog.common.models.DTORequestParams;
import lombok.Data;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Repository
@FeignClient(name = "springblog-microservice2")
@RequestMapping("/DTOUtil")
public interface DTOUtilClient {

    // TODO
    @RequestMapping(value = "/map", method = POST)
    <S, T> T map(@RequestBody DTORequestParams<S, T> dtoRequestParams);
//    <S, T> T map(@RequestBody S source, @RequestParam("targetClass") Class<T> targetClass);

    // TODO
    default <S, T> T map(S source, Class<T> targetClass) {
        DTORequestParams<S, T> dtoRequestParams = new DTORequestParams<S, T>(source, null, targetClass, null);
        return map(dtoRequestParams);
    }

    // TODO
    @RequestMapping(value = "/mapTo", method = POST)
    <S, T> void mapTo(@RequestBody DTORequestParams<S, T> dtoRequestParams);
//    <S, T> void mapTo(@RequestBody S source, @RequestParam("dist") T dist);

    // TODO
    default <S, T> void mapTo(S source, T dist) {
        mapTo(new DTORequestParams<S, T>(source, null, null, dist));
    }

    // TODO
    @RequestMapping(value = "/mapList", method = POST)
    <S, T> List<T> mapList(@RequestBody DTORequestParams<S, T> dtoRequestParams);
//    <S, T> List<T> mapList(@RequestBody List<S> sources, @RequestParam("targetClass") Class<T> targetClass);

    // TODO
    default <S, T> List<T> mapList(List<S> sources, Class<T> targetClass) {
        return mapList(new DTORequestParams<S, T>(null, sources, targetClass, null));
    }

}
