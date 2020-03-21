package com.ljqiii.hairlessauth.service;

import com.ljqiii.hairlessauth.dao.UserMapper;
import com.ljqiii.hairlessauth.dao.UserRoleMapper;
import com.ljqiii.hairlesscommon.domain.User;
import com.ljqiii.hairlesscommon.exception.UserException;
import com.ljqiii.hairlesscommon.form.UserReg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserMapper userMapper;

    @Autowired(required = false)
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Transactional
    public User newUser(UserReg userReg) {

        if (userMapper.selectUserByUserName(userReg.getUsername()) != null) {
            throw new UserException(UserException.ERROR.USER_ALREADY_EXISTS);
        }

        User user = User.builder()
                .userName(userReg.getUsername())
                .encodedPassword(passwordEncoder.encode(userReg.getPassword()))
                .eMail(userReg.getEmail())
                .build();

        if (userReg.getRole().equals("teacher")) {
            user.setEnabled(false);
        } else if (userReg.getRole().equals("normaluser")) {
            user.setEnabled(true);
        } else {
            throw new UserException(UserException.ERROR.INSERT_FAIL);
        }

        int i = userMapper.insertUser(user);

        Integer userId = user.getId();

        userRoleMapper.insert(userId,userRoleMapper.selectRoleByName(userReg.getRole()).getId());
        return user;
    }
}
