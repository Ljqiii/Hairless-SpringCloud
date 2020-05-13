package com.ljqiii.hairlessconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class HairlessConfigserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(HairlessConfigserverApplication.class, args);
    }

}
