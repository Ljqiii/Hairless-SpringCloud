package com.ljqiii.hairlesscompetition.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class QuartzSchedulerService {
    @Autowired
    Scheduler scheduler;

    /**
     * 添加 simplejob，在start和endtime之间重复
     *
     * @param jobClass
     * @param name
     * @param groupName
     * @param jobDataMap
     * @param startTime
     * @param endTime
     * @param intervalSeconds
     */
    public void addSimpleJob(Class<? extends QuartzJobBean> jobClass,
                             String name, String groupName,
                             JobDataMap jobDataMap,
                             Date startTime, Date endTime, Integer intervalSeconds) {

        JobBuilder jobBuilder = JobBuilder.newJob(jobClass)
                .withIdentity("Job_" + name, "JobGroup_" + groupName);

        if (jobDataMap != null) {
            jobBuilder.usingJobData(jobDataMap);
        }

        JobDetail jobDetail = jobBuilder.build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .startAt(startTime)
                .endAt(endTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(intervalSeconds)
                        .withRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY))
                .withIdentity("Trigger_" + name, "TriggerGroup_" + groupName)
                .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("Schedule Start fail", e);
        }
    }

    /**
     * 删除job
     *
     * @param jobName
     * @param jobGroupName
     */
    public void deletejob(String jobName, String jobGroupName) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, jobGroupName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, jobGroupName));
            scheduler.deleteJob(new JobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            log.error("job delete fail", e);
        }
    }

    //https://github.com/xkcoding/spring-boot-demo/blob/master/spring-boot-demo-task-quartz/src/main/java/com/xkcoding/task/quartz/service/impl/JobServiceImpl.java
}
