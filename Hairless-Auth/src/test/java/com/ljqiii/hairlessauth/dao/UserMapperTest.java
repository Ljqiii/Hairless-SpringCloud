package com.ljqiii.hairlessauth.dao;

import com.ljqiii.hairlessauth.HairlessAuthApplicationTests;
import com.ljqiii.hairlesscommon.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserMapperTest  extends HairlessAuthApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void selectUserById() {
        User user = userMapper.selectUserByUserName("user");
        user.getAuthorities();
        System.out.println(1);

    }

    @Test
    void selectAuthoritiesByUserId() {
    }
}