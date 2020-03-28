package com.ljqiii.hairlessaccount.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Before
    public void setUp() throws Exception {
    }



    @Test
    public void updateLastLoginTime() {
        Date date = new Date(new Date().getTime());
        System.out.println(date.toString());
        userMapper.updateLastLoginTime("aaa", date);
    }

}