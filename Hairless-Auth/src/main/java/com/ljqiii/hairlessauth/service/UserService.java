package com.ljqiii.hairlessauth.service;

import com.ljqiii.hairlessauth.dao.UserMapper;
import com.ljqiii.hairlessauth.dao.UserRoleMapper;
import com.ljqiii.hairlesscommon.domain.Point;
import com.ljqiii.hairlesscommon.domain.User;
import com.ljqiii.hairlesscommon.enums.PointEventEnum;
import com.ljqiii.hairlesscommon.exception.UserException;
import com.ljqiii.hairlessauth.form.UserReg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class UserService {

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


        user.setEnabled(true);

        int i = userMapper.insertUser(user);

        Integer userId = user.getId();

        userRoleMapper.insert(userId, userRoleMapper.selectRoleByName(userReg.getRole()).getId());
        return user;
    }

    //更新最后登录时间
    @Transactional
    public void updateLastLoginTime(String userName, Date time) {
        int i = userMapper.updateLastLoginTime(userName, time);
    }

    //增加每日登录积分
    @Transactional
    public void addLoginPoint(String username) {
        Date lastlogintime = userMapper.selectLastlogintime(username);
        Date now = new Date();
        if (!DateUtils.isSameDay(lastlogintime, now)) {
            Point point = new Point();
            point.setUsername(username);
            point.setCreatetime(now);
            point.setEventid(PointEventEnum.LOGIN_EVERYDAY.getEventid());
        }
    }
}
