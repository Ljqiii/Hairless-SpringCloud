package com.ljqiii.hairlessauth.config;

import com.ljqiii.hairlessauth.dao.UserMapper;
import com.ljqiii.hairlesscommon.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/login", "/oauth/authorize")
                .and().authorizeRequests().anyRequest().authenticated()
                .and().formLogin().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean());
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder.encode("ps"))
//                .roles("USER");
    }

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
}
