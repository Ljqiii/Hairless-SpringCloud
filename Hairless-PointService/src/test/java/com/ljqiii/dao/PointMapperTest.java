package com.ljqiii.dao;

import com.ljqiii.HairlessPointserviceApplicationTests;
import com.ljqiii.hairlesscommon.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PointMapperTest extends HairlessPointserviceApplicationTests {


    @Autowired
    PointMapper pointMapper;

    @Test
    void countPointByUser() {
        int i = pointMapper.countPointByUser(User.builder().id(6).build());
        System.out.println(i);
    }

    @Test
    void insertPoint() {
    }

    @Test
    void deletePoint() {
    }
}