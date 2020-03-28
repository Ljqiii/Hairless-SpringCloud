package com.ljqiii.hairlessaccount.client;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", fallback = NotificationClient.NotificationServiceFallback.class)
public interface NotificationClient {

    @PostMapping("/getunreadcount")
    HairlessResponse<JSONObject> unReadNotificationCount(@RequestBody String username);

    static class NotificationServiceFallback {

    }
}
