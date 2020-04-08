package com.ljqiii.hairlessmain.service;


import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlessmain.service.impl.ProblemServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTest {

    @Autowired
    SubmitService submitService;

    @Autowired
    ProblemServiceImpl problemService;

    @Test
    public void acceptance(){
        HashMap<Integer, String> acceptance = problemService.acceptance(Arrays.asList(
                Problem.builder().id(1).build(),
                Problem.builder().id(2).build(),
                Problem.builder().id(4).build()
        ));

        Assert.assertNotNull(acceptance);
    }

    @Test
    public void accuracyDataTest() {
        HashMap<String, Integer> accuracyData = submitService.userAccuracyData("aaa");
        Assert.assertNotNull(accuracyData);
    }
}
