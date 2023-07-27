package com.raysmond.blog.microservice1.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bvn13 on 11.12.2017.
 */
@Component
@RequestMapping("/PaginatorUtil")
public class PaginatorUtil {


    @RequestMapping(value = "/createPagesListPageSize", method = RequestMethod.GET)
    @ResponseBody
    public static List<Integer> createPagesList(@RequestParam("from") Integer from, @RequestParam("to") Integer to, @RequestParam("pageSize") Integer pageSize) {
        List<Integer> result = new ArrayList<>();
        Integer lastPage = (int) Math.ceil(to / pageSize);
        for (int i = from; i <= lastPage; i++) {
            result.add(i);
        }
        return result;
    }

    @RequestMapping(value = "/createPagesList", method = RequestMethod.GET)
    @ResponseBody
    public static List<Integer> createPagesList(@RequestParam("from") Integer from, @RequestParam("to") Integer to) {
        List<Integer> result = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            result.add(i);
        }
        return result;
    }


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Integer from = 1;
        Integer to = 4;

        switch (method) {
            case "all":
                for (int i = 0; i < 3; i++) {
                    createPagesList_test(from, to);
                }
                break;
            case "createPagesList":
                createPagesList_test(from, to);
                break;
        }

        return "test";
    }

    public static List<Integer> createPagesList_test(Integer from, Integer to) {
        List<Integer> result = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            result.add(i);
        }

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
        // import org.apache.lucene.util.RamUsageEstimator;
//        System.out.println(RamUsageEstimator.sizeOf(result));

        return result;
    }

}
