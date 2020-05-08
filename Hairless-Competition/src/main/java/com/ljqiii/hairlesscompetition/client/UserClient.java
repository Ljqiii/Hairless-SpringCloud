package com.ljqiii.hairlesscompetition.client;

import com.ljqiii.hairlesscommon.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service")
public interface UserClient {

    @PostMapping("/getUserInfo")
    User getUserInfo(@RequestBody String username);
}
