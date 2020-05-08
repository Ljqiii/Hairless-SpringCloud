package com.ljqiii.hairlesscompetition.service;

import com.alibaba.fastjson.JSONArray;
import com.ljqiii.hairlesscommon.domain.Competition;
import com.ljqiii.hairlesscompetition.dao.CompetitionMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompetitionLeaderServiceTest {

    @Autowired
    CompetitionLeaderService competitionLeaderService;

    @Autowired
    CompetitionMapper competitionMapper;

    Competition competition;

    @BeforeEach
    void setUp() {
        competition = competitionMapper.selecetCompetitionById(2);
    }

    @Test
    void executeInternal() {
    }

    @Test
    void calcLeaderBoard() {
        competitionLeaderService.calcLeaderBoard(2);
    }

    @Test
    void generateLeaderBoardMetaInfo() {
        JSONArray objects = competitionLeaderService.generateLeaderBoardMetaInfo(competition);
        Assert.assertNotNull(objects);
    }

    @Test
    void newMetaItem() {
    }
}
