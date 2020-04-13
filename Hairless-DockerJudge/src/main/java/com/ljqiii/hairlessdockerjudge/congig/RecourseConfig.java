package com.ljqiii.hairlessdockerjudge.congig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class RecourseConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .anonymous()
                .and()
                .authorizeRequests().antMatchers("/api/me").permitAll()
        .antMatchers("/api/public").authenticated();
    }
}
