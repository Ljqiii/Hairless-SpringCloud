package com.ljqiii.hairlessauth.config;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlessauth.dao.UserMapper;
import com.ljqiii.hairlessauth.service.UserService;
import com.ljqiii.hairlesscommon.domain.User;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Configuration
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Setter
    @Getter
    @Value("${rememberme-key}")
    String rememberme_key;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/register", "/ping").permitAll()
                .and()
                .requestMatchers().antMatchers("/authenticatedtest", "/login", "/oauth/authorize")
                .and().authorizeRequests().anyRequest().authenticated()
                .and().formLogin().permitAll();


        //未认证返回401，并禁止/login
//        http.exceptionHandling(e -> {
//                    e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
//                }
//        );


        http.formLogin().failureHandler((request, response, exception) -> {
                    HairlessResponse<Void> hairlessResponse = new HairlessResponse<>();
                    hairlessResponse.setCodeMsg(ResultEnum.UNAUTHORIZED);
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    PrintWriter writer = response.getWriter();

                    writer.print(JSONObject.toJSONString(hairlessResponse));
                    writer.flush();
                }
        );

        //成功返回json
        http.formLogin().successHandler((request, response, exception) -> {
            HairlessResponse<Void> hairlessResponse = new HairlessResponse<>();
            hairlessResponse.setCodeMsg(ResultEnum.LOGIN_SUCCESS);
            response.setStatus(HttpStatus.OK.value());
            PrintWriter writer = response.getWriter();

            Object principal;
            if ((principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal()) instanceof User) {
                String userName = ((User) principal).getUserName();

                userService.addLoginPoint(userName);
                userService.updateLastLoginTime(userName, new Date());
            }

            writer.print(JSONObject.toJSONString(hairlessResponse));
            writer.flush();
        });

//        http.logout().logoutSuccessHandler(new LogoutSuccessHandler() {
//            @Override
//            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                HairlessResponse<Void> hairlessResponse = new HairlessResponse<>();
//                hairlessResponse.setCodeMsg(ResultEnum.LOGOUT_SUCCESS);
//                response.setStatus(HttpStatus.OK.value());
//                PrintWriter writer = response.getWriter();
//
//                writer.print(JSONObject.toJSONString(hairlessResponse));
//                writer.flush();
//            }
//        });

        http.cors();
        http.rememberMe().key(rememberme_key);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean());
    }

    public UserDetailsService userDetailsServiceBean() throws Exception {
        return (s) -> {
            User user = userMapper.selectUserByUserName(s);
            if (user == null) {
                throw new UsernameNotFoundException("用户名或密码错误");//没这个用户
            }
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
