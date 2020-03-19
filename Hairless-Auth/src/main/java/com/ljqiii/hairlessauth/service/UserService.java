package com.ljqiii.hairlessauth.service;

import com.ljqiii.hairlessauth.dao.UserMapper;
import com.ljqiii.hairlessauth.dao.UserRoleMapper;
import com.ljqiii.hairlesscommon.domain.User;
import com.ljqiii.hairlesscommon.domain.UserRole;
import com.ljqiii.hairlesscommon.exception.NewUserException;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Transactional
    public User newUser(UserReg userReg) {

        if (userMapper.selectUserByUserName(userReg.getUsername()) != null) {
            throw new NewUserException("用户名经存在");
        }

        User user = User.builder()
                .userName(userReg.getUsername())
                .encodedPassword(passwordEncoder.encode(userReg.getPassword()))
                .eMail(userReg.getEmail())
                .isVip(false)
                .build();

        Integer roleid = null;
        if (userReg.getRole().equals("teacher")) {
            user.setEnabled(false);
        } else if (userReg.getRole().equals("normaluser")) {
            user.setEnabled(true);
        } else {
            throw new NewUserException("添加用户失败");
        }

        int i = userMapper.insertUser(user);

        Integer userId = user.getId();
        UserRole userRole = new UserRole(userId, userRoleMapper.selectRoleByName(userReg.getRole()).getId());

        userRoleMapper.insert(userRole);
        return user;
    }

}
