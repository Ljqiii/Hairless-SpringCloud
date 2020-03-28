package com.ljqiii.hairlessaccount.service;

import java.util.Date;


public interface AccountService {

    void sendLogin(String username, Date time);

    void sendLogin(String username);

}
