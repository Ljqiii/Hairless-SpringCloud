package com.ljqiii.hairlessdockerjudge.service;

import com.ljqiii.hairlessdockerjudge.comsumer.UserInfoComsumer;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestExcludeConsumer.class)
class DockerJudgeServiceTest {

    @Autowired
    UserInfoComsumer userInfoComsumer;

    @Autowired
    DockerJudgeService dockerJudgeService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void assertUserInfoNull(){
        Assert.assertNotNull(dockerJudgeService);
    }

    @Test
    void submitCode() {
        Assert.assertNotNull(userInfoComsumer);
    }

}
