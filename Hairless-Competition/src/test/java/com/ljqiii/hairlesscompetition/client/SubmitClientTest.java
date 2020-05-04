package com.ljqiii.hairlesscompetition.client;

import com.ljqiii.hairlesscommon.domain.Submit;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SubmitClientTest {

    @Autowired
    SubmitClient submitClient;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getSubmits() {
        List<Submit> submits = submitClient.getSubmits(Arrays.asList(1, 2));
        Assert.assertNotNull(submits);
        int a = 1;
    }
}
