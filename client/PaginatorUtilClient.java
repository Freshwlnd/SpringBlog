package com.raysmond.blog.microservice1.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Repository
@FeignClient(name = "springblog-microservice3")
@RequestMapping("/PaginatorUtil")
public interface PaginatorUtilClient {

    @RequestMapping(value = "/createPagesListWithPageSize", method = GET)
    @ResponseBody
    List<Integer> createPagesList(@RequestParam("from") Integer from, @RequestParam("to") Integer to, @RequestParam("pageSize") Integer pageSize);

    @RequestMapping(value = "/createPagesList", method = POST)
    @ResponseBody
    List<Integer> createPagesList(@RequestParam("from") Integer from, @RequestParam("to") Integer to);

}
