package com.ljqiii.hairlesspoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableEurekaClient
public class HairlessPointserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HairlessPointserviceApplication.class, args);
    }

}
