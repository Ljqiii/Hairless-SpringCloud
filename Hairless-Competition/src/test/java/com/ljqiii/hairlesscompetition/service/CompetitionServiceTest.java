package com.ljqiii.hairlesscompetition.service;

import com.ljqiii.hairlesscommon.vo.CompetitionVO;
import com.ljqiii.hairlesscompetition.dao.CompetitionMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class CompetitionServiceTest {

    @Autowired
    PasswordEncoder passwordEncoder;

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

    @Test
    void encode(){
        String encode = passwordEncoder.encode("123456");
        Assert.assertNotNull(encode);
    }
}
