//package com.ljqiii.hairlessauth.config;
//
//import com.netflix.discovery.converters.Auto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
//import org.springframework.security.web.access.AccessDeniedHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.security.KeyPair;
//import java.util.Arrays;
//import java.util.Collection;
//
//
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Autowired
//    UserDetailsService userDetailsService;
//
//    private TokenStore tokenStore = new InMemoryTokenStore();
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(authenticationManager)
//                .tokenStore(tokenStore)
//                .userDetailsService(userDetailsService);
//    }
//
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security
//                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()")
//                .passwordEncoder(passwordEncoder);
//
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//                       clients.inMemory()
//                .withClient("browser")
//                .authorizedGrantTypes("refresh_token", "password")
//                .scopes("ui");
//
//        ;
//    }
//}
