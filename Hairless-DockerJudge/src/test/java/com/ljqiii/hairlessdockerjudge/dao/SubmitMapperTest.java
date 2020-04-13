package com.ljqiii.hairlessdockerjudge.dao;

import com.ljqiii.hairlesscommon.domain.Submit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubmitMapperTest {
    @Autowired
    SubmitMapper submitMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void insertSubmit() {

        Submit build = Submit.builder().problemid(2).username("asa").submitedCode("sdaf").build();
        int i = submitMapper.insertSubmit(build);
        int a=1;
    }
}
