package com.ljqiii.hairlessdockerjudge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class HairlessDockerjudgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HairlessDockerjudgeApplication.class, args);
    }

}
