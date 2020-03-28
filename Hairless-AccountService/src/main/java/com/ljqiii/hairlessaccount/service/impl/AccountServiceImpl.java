package com.ljqiii.hairlessaccount.service.impl;

import com.ljqiii.hairlessaccount.service.AccountService;
import com.ljqiii.hairlesscommon.domain.amqpdomain.LoginInfo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;


@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Override
    public void sendLogin(String username, Date loginTime) {
        LoginInfo loginInfo = LoginInfo.builder().username(username).logintime(loginTime).build();
        amqpTemplate.convertAndSend("logininfo", "logininfo.time", loginInfo);
    }

    @Override
    public void sendLogin(String username) {
        sendLogin(username, new Date(new Date().getTime()));
    }
}
