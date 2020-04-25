package com.ljqiii.hairlesscompetition.service;

import com.ljqiii.hairlesscommon.vo.CompetitionVO;
import com.ljqiii.hairlesscompetition.dao.CompetitionMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CompetitionServiceTest {

    @Autowired
    CompetitionMapper competitionMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void listCompetition() {

        List<CompetitionVO> aaa = competitionMapper.selectCompetitionVO(null, null, false, "aaa");
        Assert.assertNotNull(aaa);
    }
}
