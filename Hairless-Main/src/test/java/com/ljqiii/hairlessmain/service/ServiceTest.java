package com.ljqiii.hairlessmain.service;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.URL;
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

    @Test
    public void testGetClassPathFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("file/demo.zip");
        String file = resource.getFile();
        File file1 = new File(resource.getFile());
        Assert.assertNotNull(resource);
        Assert.assertNotNull(file);
        Assert.assertNotNull(file1);
    }


}
