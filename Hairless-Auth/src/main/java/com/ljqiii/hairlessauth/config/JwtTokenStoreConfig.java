package com.ljqiii.hairlessauth.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenStoreConfig implements InitializingBean {

    @Setter
    @Getter
    String signingKey;

    @Bean(name = "jwtTokenStore")
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(getSigningKey());
        return accessTokenConverter;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isEmpty(signingKey)) {
            throw new RuntimeException("SigningKey can not empty");
        }

    }
}
