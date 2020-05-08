package com.ljqiii.hairlesscompetition.client;

import com.ljqiii.hairlesscommon.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserClientTest {
    @Autowired
    UserClient userClient;

    @Test
    void getUserInfo() {

        User aaa = userClient.getUserInfo("aaa");
        Assert.assertNotNull(aaa);
    }
}
