package com.ljqiii.hairlessaccount.service;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notification-service", fallback = NotificationService.NotificationServiceFallback.class)
public interface NotificationService {

    @PostMapping("/getunreadcount")
    HairlessResponse<JSONObject> unReadNotificationCount(String username);

    static class NotificationServiceFallback {

    }
}
