package com.ljqiii.hairlessnotification.service;

import com.ljqiii.hairlesscommon.domain.User;

public interface NotificationService {
    int unReadCount(User user);
}
