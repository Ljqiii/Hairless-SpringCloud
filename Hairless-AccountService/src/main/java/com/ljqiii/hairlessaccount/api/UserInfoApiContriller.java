package com.ljqiii.hairlessaccount.api;

import com.ljqiii.hairlessaccount.service.AccountService;
import com.ljqiii.hairlesscommon.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserInfoApiContriller {

    @Autowired
    AccountService accountService;

    @PostMapping("/getUserInfo")
    public User getUserInfo(@RequestBody String username) {
        return accountService.getUserByUserName(username);
    }
}
