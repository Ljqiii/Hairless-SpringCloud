package com.ljqiii.hairlessaccount.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notification-service", fallback = NotificationService.NotificationServiceFallback.class)
public interface NotificationService {

    @PostMapping("/api/notification/getunreadcount")
    int unReadNotificationCount(String username);

    static class NotificationServiceFallback {

    }
}
