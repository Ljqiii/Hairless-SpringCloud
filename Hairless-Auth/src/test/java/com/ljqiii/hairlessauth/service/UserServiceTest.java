package com.ljqiii.hairlessauth.service;

import com.ljqiii.HairlessAuthApplicationTests;
import com.ljqiii.hairlesscommon.domain.User;
import com.ljqiii.hairlessauth.form.UserReg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


class UserServiceTest extends HairlessAuthApplicationTests {

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void register() {

        UserReg userReg = new UserReg();
        userReg.setEmail("sdf@sd.com");
        userReg.setPassword("pw");
        userReg.setRole("teacher");

        userReg.setUsername("user");

        User user = userService.newUser(userReg);
        Assert.notNull(user);
    }
}