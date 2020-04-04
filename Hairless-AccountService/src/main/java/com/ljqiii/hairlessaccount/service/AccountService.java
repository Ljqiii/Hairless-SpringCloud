package com.ljqiii.hairlessaccount.service;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.domain.User;

import java.util.Date;


public interface AccountService {

    void sendLogin(String username, Date time);

    void sendLogin(String username);

    User getUserByUserName(String username);

}
