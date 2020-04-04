package com.ljqiii.hairlessmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class HairlessMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(HairlessMainApplication.class, args);
    }

}
