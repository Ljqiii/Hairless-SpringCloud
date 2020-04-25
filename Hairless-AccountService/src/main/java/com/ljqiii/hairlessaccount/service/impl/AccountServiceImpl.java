package com.ljqiii.hairlessaccount.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlessaccount.dao.UserMapper;
import com.ljqiii.hairlessaccount.service.AccountService;
import com.ljqiii.hairlesscommon.domain.User;
import com.ljqiii.hairlesscommon.domain.amqpdomain.LoginInfo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void sendLogin(String username, Date loginTime) {
        LoginInfo loginInfo = LoginInfo.builder().username(username).logintime(loginTime).build();
        amqpTemplate.convertAndSend("logininfo", "logininfo.time", loginInfo);
    }

    @Override
    public void sendLogin(String username) {
        sendLogin(username, new Date(new Date().getTime()));
    }

    @Override
    public User getUserByUserName(String username) {
        return userMapper.selecetUserByUserName(username);
    }

    @Override
    public void changePassword(String username, String newPassword) {
        String encodedpassword = passwordEncoder.encode(newPassword);
        userMapper.updateEncodedPassword(username, encodedpassword);
    }

}
