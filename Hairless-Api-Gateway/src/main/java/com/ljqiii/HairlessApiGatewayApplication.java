package com.ljqiii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class HairlessApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HairlessApiGatewayApplication.class, args);
    }

}
