package com.ljqiii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HairlessAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(HairlessAuthApplication.class, args);
    }

}
