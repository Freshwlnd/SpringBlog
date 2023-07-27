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

    @RequestMapping(value = "/map", method = RequestMethod.POST)
    @ResponseBody
    public <S, T> T map(@RequestBody DTORequestParams<S, T> dtoRequestParams) ;

    @RequestMapping(value = "/mapTo", method = RequestMethod.POST)
    public <S, T> void mapTo(@RequestBody DTORequestParams<S, T> dtoRequestParams) ;

    @RequestMapping(value = "/mapList", method = RequestMethod.POST)
    @ResponseBody
    public <S, T> List<T> mapList(@RequestBody DTORequestParams<S, T> dtoRequestParams) ;

}
