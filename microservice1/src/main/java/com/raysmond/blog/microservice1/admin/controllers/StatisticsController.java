package com.raysmond.blog.microservice1.admin.controllers;

import com.raysmond.blog.common.models.dto.PostsIdListDTO;
import com.raysmond.blog.common.models.dto.VisitsStatsChartDTO;
import com.raysmond.blog.microservice1.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by bvn13 on 20.12.2017.
 */
@Controller
@RequestMapping(value = "/admin/stats")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    // TODO

    @RequestMapping(value = "testGetVisitsChart", method = RequestMethod.GET)
    @ResponseBody
    public String testGetVisitsChart() {

        getVisitsChart();

        return "test";
    }

    @RequestMapping(value = "testGetVisitsChartByPeriodAndPostsIdList", method = RequestMethod.GET)
    @ResponseBody
    public String testGetVisitsChartByPeriodAndPostsIdList() {
        Date start = new Date(1);
        Date end = new Date(1);
        PostsIdListDTO postsDto = new PostsIdListDTO();

        getVisitsChartByPeriodAndPostsIdList(start, end, postsDto);

        return "test";
    }

    ////////////////////////////////////


    @GetMapping(value = "/visits", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    VisitsStatsChartDTO getVisitsChart() {
        VisitsStatsChartDTO chart = statisticsService.getFullVisitsStatsChartData();
        return chart;
    }

    @PostMapping(value = "/visits", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    VisitsStatsChartDTO getVisitsChartByPeriodAndPostsIdList(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date start,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date end,
            @RequestBody PostsIdListDTO postsDto) {
        VisitsStatsChartDTO chart = statisticsService.getChartDataByPeriodAndPostsList(start, end, postsDto.getIds());

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println("VisitsStatsChartDTO: ");
//        System.out.println(RamUsageEstimator.sizeOf(chart));
//        System.out.println("PostsIdListDTO: ");
//        System.out.println(RamUsageEstimator.sizeOf(postsDto));

        return chart;
    }


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    @ResponseBody
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Date start = new Date(1);
        Date end = new Date(1);
        PostsIdListDTO postsDto = new PostsIdListDTO();


        if (method.equals("all") || method.equals("getVisitsChart")) {
            getVisitsChart_test();
        }
        if (method.equals("all") || method.equals("getVisitsChartByPeriodAndPostsIdList")) {
            getVisitsChartByPeriodAndPostsIdList_test(start, end, postsDto);
        }

        return "test";
    }

    public VisitsStatsChartDTO getVisitsChart_test() {
//        VisitsStatsChartDTO chart = statisticsService.getFullVisitsStatsChartData();
        VisitsStatsChartDTO chart = new VisitsStatsChartDTO();
        return chart;
    }

    public VisitsStatsChartDTO getVisitsChartByPeriodAndPostsIdList_test(Date start, Date end, PostsIdListDTO postsDto) {
//        VisitsStatsChartDTO chart = statisticsService.getChartDataByPeriodAndPostsList(start, end, postsDto.getIds());
        VisitsStatsChartDTO chart = new VisitsStatsChartDTO();
        return chart;
    }

}
