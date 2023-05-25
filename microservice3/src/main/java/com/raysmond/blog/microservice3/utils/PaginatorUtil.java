package com.raysmond.blog.microservice3.utils;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by bvn13 on 11.12.2017.
 */
@RestController
@RequestMapping("/PaginatorUtil")
public class PaginatorUtil {

    @RequestMapping(value = "/createPagesListWithPageSize", method = GET)
    public static List<Integer> createPagesList(@RequestParam("from") Integer from, @RequestParam("to") Integer to, @RequestParam("pageSize") Integer pageSize) {
        List<Integer> result = new ArrayList<>();
        Integer lastPage = (int) Math.ceil(to / pageSize);
        for (int i=from; i<=lastPage; i++) {
            result.add(i);
        }
        return result;
    }

    @RequestMapping(value = "/createPagesList", method = GET)
    public static List<Integer> createPagesList(@RequestParam("from") Integer from, @RequestParam("to") Integer to) {
        List<Integer> result = new ArrayList<>();
        for (int i=from; i<=to; i++) {
            result.add(i);
        }
        return result;
    }

    @RequestMapping(value = "/test", method = GET)
    public static String test() {
        String str = "hello";
        return str;
    }

}
