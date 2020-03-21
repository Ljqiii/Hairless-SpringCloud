package com.ljqiii.config;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

@Configuration
public class JWTOAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore;


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    DefaultTokenServices tokenServices;

    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return null;
            }
        };
        return userDetailsService;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));

        endpoints
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService());
    }


}
