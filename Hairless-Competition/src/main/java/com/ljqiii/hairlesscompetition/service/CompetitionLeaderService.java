package com.ljqiii.hairlesscompetition.service;


import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

/**
 * 排行榜计算
 */

@Service
@Slf4j
public class CompetitionLeaderService extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        String name = jobKey.getName();
        String group = jobKey.getGroup();
        log.info("exec job,key:{}, group:{}", name, group);


    }
}
