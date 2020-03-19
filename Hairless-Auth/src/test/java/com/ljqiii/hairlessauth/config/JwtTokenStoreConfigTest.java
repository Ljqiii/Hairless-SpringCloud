package com.ljqiii.hairlessauth.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Import(value = {com.ljqiii.hairlessauth.config.JwtTokenStoreConfig.class})
@SpringBootTest()
class JwtTokenStoreConfigTest {

    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    JwtTokenStoreConfig jwtTokenStoreConfig;


    @BeforeEach
    void setUp() {

    }

    @Test
    public void valueSet() {
        System.out.println(jwtTokenStoreConfig.signingKey);

    }
}