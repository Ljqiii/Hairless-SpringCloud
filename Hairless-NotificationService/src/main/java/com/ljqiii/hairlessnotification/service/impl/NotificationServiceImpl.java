package com.ljqiii.hairlessnotification.service.impl;

import com.ljqiii.hairlesscommon.domain.User;
import com.ljqiii.hairlessnotification.dao.NotificationMapper;
import com.ljqiii.hairlessnotification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationServiceImpl implements NotificationService
{

    @Autowired
    NotificationMapper notificationMapper;

    @Override
    public int unReadCount(User user) {
        return notificationMapper.selectUnReadNotificationCount(user.getId());
    }
}
