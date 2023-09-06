package com.raysmond.blog.common.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import java.util.List;

// TODO
@Data
@NoArgsConstructor
public class StatisticsServiceParams {
    private Date periodStart;
    private Date periodEnd;
    private List<Integer> postsIdList;

    public StatisticsServiceParams(Date periodStart, Date periodEnd, List<Integer> postsIdList) {
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.postsIdList = postsIdList;
    }

}
