package com.ljqiii.hairlessapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@SpringBootApplication
@EnableEurekaClient
public class HairlessApiGatewayApplication {

//    @Bean
//    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
//        http.anonymous()
//                .and().authorizeExchange()
//                .pathMatchers("/**")
//                .authenticated();
//        http.oauth2Login();
//        return http.build();
//    }

    public static void main(String[] args) {
        SpringApplication.run(HairlessApiGatewayApplication.class, args);
    }

}
