package com.raysmond.blog.microservice2.services;

import com.raysmond.blog.microservice2.models.dto.VisitsStatsChartDTO;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by bvn13 on 20.12.2017.
 */
@Service
@RequestMapping("/StatisticsService")
public class StatisticsService {

    private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    @Autowired
    private EntityManager entityManager;

    public List<Object> getVisitsStats() {

        Session session = (Session) this.entityManager.getDelegate();
        SQLQuery query = session.createSQLQuery(
                "select date_trunc('day', v.createdat) as dt, v.post_id, p.title, count(distinct v.clientip) as count " +
                        "from visits as v " +
                        "left join seo_robots_agents as ra " +
                        "on case when ra.isregexp then lower(nullif(v.useragent,'')) ~* lower(ra.useragent) " +
                        "else lower(nullif(v.useragent,'')) like concat('%',lower(ra.useragent),'%') end " +
                        "inner join posts as p " +
                        "on v.post_id = p.id " +
                        "where v.isadmin = false " +
                        "and ra.id isnull " +
                        "group by date_trunc('day', v.createdat), v.post_id, p.title"
        );

        List<Object> result = query.list();

        return result;
    }

    public List<Object> getVisitsStatsByPeriodAndPostsList(Date periodStart, Date periodEnd, List<Integer> postsIdList) {

        Session session = (Session) this.entityManager.getDelegate();
        SQLQuery query = session.createSQLQuery(
                "select data.dt, data.post_id, data.title, data.count from (" +
                        "select date_trunc('day', v.createdat) as dt, v.post_id, p.title, count(distinct v.clientip) as count " +
                        "from visits as v " +
                        "left join seo_robots_agents as ra " +
                        "on case when ra.isregexp then lower(nullif(v.useragent,'')) ~* lower(ra.useragent) " +
                        "else lower(nullif(v.useragent,'')) like concat('%',lower(ra.useragent),'%') end " +
                        "inner join posts as p " +
                        "on v.post_id = p.id " +
                        "where v.isadmin = false " +
                        "and ra.id isnull " +
                        "group by date_trunc('day', v.createdat), v.post_id, p.title " +
                        ") as data " +
                        "where data.dt >= :periodStart and data.dt <= :periodEnd and data.post_id in (:postsIdList)"
        );

        query.setParameter("periodStart", periodStart);
        query.setParameter("periodEnd", periodEnd);
        query.setParameterList("postsIdList", postsIdList);

        List<Object> result = query.list();

        return result;
    }

    private VisitsStatsChartDTO convertDataToChart(List<Object> list) {
        VisitsStatsChartDTO chart = new VisitsStatsChartDTO();
        chart.getChart().setType("line");
        chart.getTitle().setText("Visits Statistics");
        chart.getPlotOptions().setEnableMouseTracking(true);
        chart.getPlotOptions().getDataLabels().setEnabled(true);

        chart.getYAxis().getTitle().setText("Count");

        Set<Date> dates = new HashSet<>();

        Map<String, Map<Date, Long>> data = new HashMap<>();


        for (Object vs : list) {
            Object[] vsa = (Object[]) vs;

            Date statDt = (Date) vsa[0];
            String statPost = String.format("%d / %s", ((BigInteger) vsa[1]).longValue(), (String) vsa[2]);
            Long statCount = ((BigInteger) vsa[3]).longValue();

            dates.add(statDt);

            if (!data.containsKey(statPost)) {
                data.put(statPost, new HashMap<>());
            }
            data.get(statPost).put(statDt, statCount);
        }

        List<Date> sortedDates = new ArrayList<>(dates);
        Collections.sort(sortedDates);

        for (Date dt : sortedDates) {
            chart.getXAxis().getCategories().add(df.format(dt));
        }

        List<String> sortedTitles = new ArrayList<>(data.keySet());
        Collections.sort(sortedTitles);

        for (String statPost : sortedTitles) {

            VisitsStatsChartDTO.Series series = new VisitsStatsChartDTO.Series();
            series.setName(statPost);

            for (Date dt : sortedDates) {
                if (data.get(statPost).containsKey(dt)) {
                    series.getData().add(data.get(statPost).get(dt));
                } else {
                    series.getData().add(0L);
                }
            }

            chart.getSeries().add(series);
        }

        return chart;
    }

    public VisitsStatsChartDTO getFullVisitsStatsChartData() {
        List<Object> list = getVisitsStats();
        return convertDataToChart(list);
    }

    public VisitsStatsChartDTO getChartDataByPeriodAndPostsList(Date periodStart, Date periodEnd, List<Integer> postsIdList) {
        List<Object> list = getVisitsStatsByPeriodAndPostsList(periodStart, periodEnd, postsIdList);
        return convertDataToChart(list);
    }


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Date start = new Date(1);
        Date end = new Date(100);
        List<Integer> postsIdList = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        postsIdList.add(1);
        postsIdList.add(2);

        switch (method) {
            case "all":
                for (int i = 0; i < 1; i++) {
                    getVisitsStats_test();
                }
                for (int i = 0; i < 1; i++) {
                    getVisitsStatsByPeriodAndPostsList_test(start, end, postsIdList);
                }
                for (int i = 0; i < 1; i++) {
                    convertDataToChart_test(list);
                }
                for (int i = 0; i < 1; i++) {
                    getFullVisitsStatsChartData_test();
                }
                for (int i = 0; i < 1; i++) {
                    getChartDataByPeriodAndPostsList_test(start, end, postsIdList);
                }
                break;
            case "getMainUri":
                getVisitsStats_test();
                break;
            case "setMainUri":
                getVisitsStatsByPeriodAndPostsList_test(start, end, postsIdList);
                break;
            case "getMainUriStripped":
                convertDataToChart_test(list);
                break;
            case "getPageSize":
                getFullVisitsStatsChartData_test();
                break;
            case "setPageSize":
                getChartDataByPeriodAndPostsList_test(start, end, postsIdList);
                break;
        }

        return "test";
    }


    List<Object> getVisitsStats_test() {

        Session session = (Session) this.entityManager.getDelegate();
        SQLQuery query = session.createSQLQuery(
                "select date_trunc('day', v.createdat) as dt, v.post_id, p.title, count(distinct v.clientip) as count " +
                        "from visits as v " +
                        "left join seo_robots_agents as ra " +
                        "on case when ra.isregexp then lower(nullif(v.useragent,'')) ~* lower(ra.useragent) " +
                        "else lower(nullif(v.useragent,'')) like concat('%',lower(ra.useragent),'%') end " +
                        "inner join posts as p " +
                        "on v.post_id = p.id " +
                        "where v.isadmin = false " +
                        "and ra.id isnull " +
                        "group by date_trunc('day', v.createdat), v.post_id, p.title"
        );

        List<Object> result = query.list();

        return result;
    }

    List<Object> getVisitsStatsByPeriodAndPostsList_test(Date periodStart, Date periodEnd, List<Integer> postsIdList) {

        Session session = (Session) this.entityManager.getDelegate();
        SQLQuery query = session.createSQLQuery(
                "select data.dt, data.post_id, data.title, data.count from (" +
                        "select date_trunc('day', v.createdat) as dt, v.post_id, p.title, count(distinct v.clientip) as count " +
                        "from visits as v " +
                        "left join seo_robots_agents as ra " +
                        "on case when ra.isregexp then lower(nullif(v.useragent,'')) ~* lower(ra.useragent) " +
                        "else lower(nullif(v.useragent,'')) like concat('%',lower(ra.useragent),'%') end " +
                        "inner join posts as p " +
                        "on v.post_id = p.id " +
                        "where v.isadmin = false " +
                        "and ra.id isnull " +
                        "group by date_trunc('day', v.createdat), v.post_id, p.title " +
                        ") as data " +
                        "where data.dt >= :periodStart and data.dt <= :periodEnd and data.post_id in (:postsIdList)"
        );

        query.setParameter("periodStart", periodStart);
        query.setParameter("periodEnd", periodEnd);
        query.setParameterList("postsIdList", postsIdList);

        List<Object> result = query.list();

        return result;
    }

    VisitsStatsChartDTO convertDataToChart_test(List<Object> list) {
        VisitsStatsChartDTO chart = new VisitsStatsChartDTO();
        chart.getChart().setType("line");
        chart.getTitle().setText("Visits Statistics");
        chart.getPlotOptions().setEnableMouseTracking(true);
        chart.getPlotOptions().getDataLabels().setEnabled(true);

        chart.getYAxis().getTitle().setText("Count");

        Set<Date> dates = new HashSet<>();

        Map<String, Map<Date, Long>> data = new HashMap<>();


        for (Object vs : list) {
            Object[] vsa = (Object[]) vs;

            Date statDt = (Date) vsa[0];
            String statPost = String.format("%d / %s", ((BigInteger) vsa[1]).longValue(), (String) vsa[2]);
            Long statCount = ((BigInteger) vsa[3]).longValue();

            dates.add(statDt);

            if (!data.containsKey(statPost)) {
                data.put(statPost, new HashMap<>());
            }
            data.get(statPost).put(statDt, statCount);
        }

        List<Date> sortedDates = new ArrayList<>(dates);
        Collections.sort(sortedDates);

        for (Date dt : sortedDates) {
            chart.getXAxis().getCategories().add(df.format(dt));
        }

        List<String> sortedTitles = new ArrayList<>(data.keySet());
        Collections.sort(sortedTitles);

        for (String statPost : sortedTitles) {

            VisitsStatsChartDTO.Series series = new VisitsStatsChartDTO.Series();
            series.setName(statPost);

            for (Date dt : sortedDates) {
                if (data.get(statPost).containsKey(dt)) {
                    series.getData().add(data.get(statPost).get(dt));
                } else {
                    series.getData().add(0L);
                }
            }

            chart.getSeries().add(series);
        }

        return chart;
    }

    public VisitsStatsChartDTO getFullVisitsStatsChartData_test() {
//        List<Object> list = getVisitsStats();
        List<Object> list = new ArrayList<>();
//        return convertDataToChart(list);
        return new VisitsStatsChartDTO();
    }

    public VisitsStatsChartDTO getChartDataByPeriodAndPostsList_test(Date periodStart, Date periodEnd, List<Integer> postsIdList) {
//        List<Object> list = getVisitsStatsByPeriodAndPostsList(periodStart, periodEnd, postsIdList);
        List<Object> list = new ArrayList<>();
//        return convertDataToChart(list);
        return new VisitsStatsChartDTO();
    }
}
