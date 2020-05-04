package com.ljqiii.hairlesscompetition.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class QuartzSchedulerServiceTest {

    @Autowired
    QuartzSchedulerService quartzSchedulerService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void addSimpleJob() {
        Assert.assertNotNull(quartzSchedulerService);
        Date now = new Date();
        Date nowadd6 = new Date();
        nowadd6.setTime(now.getTime() + 600000);

//        quartzSchedulerService.addSimpleJob(SimpleJobT.class, "nneawaame", "ggraoup", null, now, nowadd6, 1);
    }

    @Test
    public void detetejob() {

        quartzSchedulerService.deletejob("Job_nneawaame", "Job_ggraoup");
        quartzSchedulerService.deletejob("Job_nneawame", "Job_ggroup");

    }

}
