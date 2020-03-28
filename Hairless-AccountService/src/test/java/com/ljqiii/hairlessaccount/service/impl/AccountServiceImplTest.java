package com.ljqiii.hairlessaccount.service.impl;

import com.ljqiii.HairlessAccountserviceApplicationTests;
import com.ljqiii.hairlessaccount.service.AccountService;
import com.ljqiii.hairlesscommon.domain.amqpdomain.LoginInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceImplTest {

    @Autowired
    AccountService accountService;

    @Autowired
    ApplicationContext applicationContext;

    LoginInfo loginInfo;

    @Test
    public void applicationContextNotNull() {
        Assert.assertNotNull(applicationContext);
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void sendLogin() {

        accountService.sendLogin("u");
    }
}