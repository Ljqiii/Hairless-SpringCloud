package com.ljqiii.hairlessauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    String redirecturl = "http://hairless.ljqiii.xyz:8082/login/oauth2/code/cclient";
    String redirecturl2 = "http://hairless.ljqiii.xyz:8082/login/oauth2/code/gateway";
    String redirecturl3 = "http://hairless.ljqiii.xyz:7082/login/oauth2/code/gateway";

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("hairless-gateway")
                .secret(passwordEncoder().encode("pw"))
                .authorizedGrantTypes("authorization_code")
                .scopes("ui")
                .autoApprove(true)
                .redirectUris(redirecturl, redirecturl2, redirecturl3)

                .and()
                .withClient("browser")
                .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                .scopes("ui")

                .and()
                .withClient("point-service")
                .secret(passwordEncoder().encode("pw"))
                .authorizedGrantTypes("client_credentials", "refresh_token", "password")
                .scopes("ui", "server")

                .and()
                .withClient("dockerjudge-service")
                .secret(passwordEncoder().encode("pw"))
                .authorizedGrantTypes("client_credentials", "refresh_token", "password")
                .scopes("ui", "server")

                .and()
                .withClient("notification-service")
                .secret(passwordEncoder().encode("pw"))
                .authorizedGrantTypes("client_credentials", "refresh_token", "password")
                .scopes("ui", "server")

                .and()
                .withClient("competition-service")
                .secret(passwordEncoder().encode("pw"))
                .authorizedGrantTypes("client_credentials", "refresh_token", "password")
                .scopes("ui", "server")

                .and()
                .withClient("account-service")
                .secret(passwordEncoder().encode("pw"))
                .authorizedGrantTypes("client_credentials", "refresh_token", "password")
                .scopes("ui", "server")

                .and()
                .withClient("main-service")
                .secret(passwordEncoder().encode("pw"))
                .authorizedGrantTypes("client_credentials", "refresh_token", "password")
                .scopes("ui", "server")

                .and()
                .withClient("forum-service")
                .secret(passwordEncoder().encode("pw"))
                .authorizedGrantTypes("client_credentials", "refresh_token", "password")
                .scopes("ui", "server")

                .and()
                .withClient("gateway")
                .secret(passwordEncoder().encode("pw"))
                .authorizedGrantTypes("authorization_code")
                .scopes("ui")
                .autoApprove(true)
                .redirectUris(redirecturl, redirecturl2, redirecturl3);

    }
}
