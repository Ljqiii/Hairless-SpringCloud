package com.ljqiii.hairlessapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class HairlessApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HairlessApiGatewayApplication.class, args);
    }

}
