package com.ljqiii.hairlessaccount.comsumer;

import com.ljqiii.hairlesscommon.domain.Point;
import com.ljqiii.hairlessaccount.dao.UserMapper;
import com.ljqiii.hairlessaccount.client.PointClient;
import com.ljqiii.hairlesscommon.domain.amqpdomain.LoginInfo;
import com.ljqiii.hairlesscommon.enums.PointEventEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

//TODO: 移到PointService里
@Slf4j
@Component
@RabbitListener(queues = "logininfoTime")
public class UserInfoComsumer {

    @Autowired
    PointClient pointClient;

    @Autowired
    UserMapper userMapper;

    //更新最后登录时间，增加登录积分
    @RabbitHandler
    public void process(LoginInfo loginInfo) {
        log.debug("Process Queue logininfoTime, values:{}", loginInfo);
        try {
            if (loginInfo != null && loginInfo.getUsername() != null) {
                Date lastLoginDate = userMapper.selectLastlogintime(loginInfo.getUsername());
                if (!DateUtils.isSameDay(lastLoginDate, new Date())) {
                    Point point = new Point();
                    point.setUsername(loginInfo.getUsername());
                    point.setCreatetime(loginInfo.getLogintime());
                    point.setEventid(PointEventEnum.LOGIN_EVERYDAY.getEventid());
                    pointClient.addPoint(point);
                }
                userMapper.updateLastLoginTime(loginInfo.getUsername(), loginInfo.getLogintime());
            }
        } catch (Exception e) {
            log.debug("Process Queue logininfoTime fail, values:{}, Excetion:{}", loginInfo, e);
        }
    }
}


