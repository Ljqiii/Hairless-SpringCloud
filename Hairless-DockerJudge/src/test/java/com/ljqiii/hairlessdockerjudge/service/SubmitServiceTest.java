package com.ljqiii.hairlessdockerjudge.service;

import com.ljqiii.hairlesscommon.domain.Submit;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SubmitServiceTest {

    @Autowired
    SubmitService submitService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getSubmits() {
        List<Submit> submits = submitService.getSubmits(Arrays.asList(1, 2));
        Assert.assertNotNull(submits);
    }
}
