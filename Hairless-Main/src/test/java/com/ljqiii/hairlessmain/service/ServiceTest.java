package com.ljqiii.hairlessmain.service;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTest {

    @Autowired
    SubmitService submitService;

    @Test
    public void accuracyDataTest() {
        HashMap<String, Integer> accuracyData = submitService.accuracyData("aaa");
        Assert.assertNotNull(accuracyData);
    }
}
