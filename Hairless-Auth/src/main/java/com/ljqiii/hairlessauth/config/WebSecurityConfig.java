//package com.ljqiii.hairlessauth.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//
///**
// * @author cdov
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
//        http
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .csrf().disable();
//        // @formatter:on
//    }
//
//    @Bean
//    @Primary
//    @Override
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        return new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                return new UserDetails() {
//                    @Override
//                    public Collection<? extends GrantedAuthority> getAuthorities() {
//                        return Arrays.asList(new SimpleGrantedAuthority("user"));
//                    }
//
//                    @Override
//                    public String getPassword() {
//                        return new BCryptPasswordEncoder().encode("123456");
//                    }
//
//                    @Override
//                    public String getUsername() {
//                        return username;
//                    }
//
//                    @Override
//                    public boolean isAccountNonExpired() {
//                        return true;
//                    }
//
//                    @Override
//                    public boolean isAccountNonLocked() {
//                        return true;
//                    }
//
//                    @Override
//                    public boolean isCredentialsNonExpired() {
//                        return true;
//                    }
//
//                    @Override
//                    public boolean isEnabled() {
//                        return true;
//                    }
//                };
//            }
//        };
//    }
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsServiceBean())
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }
//
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//}