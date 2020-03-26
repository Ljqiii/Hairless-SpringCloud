package com.ljqiii.hairlessauth.config;

import com.ljqiii.hairlessauth.dao.UserMapper;
import com.ljqiii.hairlesscommon.domain.Role;
import com.ljqiii.hairlesscommon.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserMapper userMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsServiceBean())
                .passwordEncoder(passwordEncoder());
    }


    @Bean
    @Primary
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return (s) -> {
            User user = userMapper.selectUserByUserName(s);
            return new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return user.getAuthorities()
                            .stream()
                            .map(a -> new SimpleGrantedAuthority(a.getName()))
                            .collect(Collectors.toList());
                }

                @Override
                public String getPassword() {
                    return user.getEncodedPassword();
                }

                @Override
                public String getUsername() {
                    return user.getUserName();
                }

                @Override
                public boolean isAccountNonExpired() {
                    return !user.isAccountExpired();
                }

                @Override
                public boolean isAccountNonLocked() {
                    return !user.isAccountLocked();
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return !user.isCredentialsExpired();
                }

                @Override
                public boolean isEnabled() {
                    return user.isEnabled();
                }
            };
        };
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .antMatchers("/user/register","/test").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();

        http.csrf().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}