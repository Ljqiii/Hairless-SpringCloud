package com.ljqiii.hairlesscompetition.client;

import com.ljqiii.hairlesscommon.apiform.GetProblemVoForm;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class MainClientTest {

    @Autowired
    MainClient mainClient;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void aaa() {
        PageData<List<ProblemListVO>> aaa = mainClient.getProblems(GetProblemVoForm.builder().problemids(Arrays.asList(1, 2, 3)).username("aaa").build());
        Assert.assertNotNull(aaa);
    }
}
