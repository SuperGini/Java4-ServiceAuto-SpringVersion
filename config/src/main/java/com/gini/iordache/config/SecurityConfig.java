package com.gini.iordache.config;


import com.gini.iordache.entity.user.Authorities;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ro.gini.iordache.security.filter.ResendTokenFilter;

import ro.gini.iordache.security.filter.TokenFilter;
import ro.gini.iordache.security.filter.UsernameAndPasswordFilter;
import ro.gini.iordache.security.handler.CacheLogoutHandler;
import ro.gini.iordache.security.handler.SecurityLogoutHandler;
import ro.gini.iordache.security.handler.SuccessfulLogoutHandler;
import ro.gini.iordache.security.provider.EmailProvider;
import ro.gini.iordache.security.provider.ResendTokenProvider;
import ro.gini.iordache.security.provider.TokenProvider;
import ro.gini.iordache.security.provider.UserNamePasswordProvider;

import java.util.Arrays;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private  UserNamePasswordProvider userNamePasswordProvider;
    @Autowired
    private  EmailProvider emailProvider;
    @Autowired
    private  TokenProvider tokenProvider;
    @Autowired
    private ResendTokenProvider resendTokenProvider;





    @Bean
    public LogoutHandler logoutHandler(){
        return new SecurityLogoutHandler();
    }

    @Bean
    public LogoutHandler cacheLogoutHandler(){
        return new CacheLogoutHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new SuccessfulLogoutHandler();
    }


    @Bean
    public LogoutFilter logoutFilter(){

        LogoutFilter logoutFilter = new LogoutFilter(logoutSuccessHandler(), cacheLogoutHandler(), logoutHandler());
        logoutFilter.setFilterProcessesUrl("/logout3");
        return logoutFilter;
    }



    @Bean
    public UsernameAndPasswordFilter usernameAndPasswordFilter(){
        try {
            return new UsernameAndPasswordFilter(authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception in: ----------------> AuthenticationManager");
        }
    }


    @Bean
    public TokenFilter tokenFilter(){

        try {
            return new TokenFilter(authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception in: ----------------> AuthenticationManager");
        }
    }


    @Bean
    public ResendTokenFilter resendTokenFilter(){

        try {
            return new ResendTokenFilter(authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception in: ----------------> AuthenticationManager");
        }
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userNamePasswordProvider);
        auth.authenticationProvider(emailProvider);
        auth.authenticationProvider(tokenProvider);
        auth.authenticationProvider(resendTokenProvider);

    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String[] roles = Arrays.stream(Authorities.values())
                                    .map(Enum::toString)
                                    .toArray(String[]::new);


        http.addFilterAt(usernameAndPasswordFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(tokenFilter(),BasicAuthenticationFilter.class)
                .addFilterBefore(resendTokenFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(logoutFilter(), LogoutFilter.class);


        http.authorizeRequests()
                        .mvcMatchers("/app/**").hasAnyRole(roles)
                        .mvcMatchers("/parts/**").hasAnyRole(roles)
                        .mvcMatchers("/clients/**").hasAnyRole(roles)
                        .mvcMatchers("/vehicles/**").hasAnyRole(roles)
                        .mvcMatchers("/labors/**").hasAnyRole(roles)
                        .mvcMatchers("/prices/**").hasAnyRole("MANAGER")
                        .mvcMatchers("/laborOrder/**").hasAnyRole(roles)
                        .mvcMatchers("/serviceOrder/**").hasAnyRole(roles)
                        .mvcMatchers("/orderPart/**").hasAnyRole(roles)
                        .mvcMatchers("/app2/**").hasAnyRole(roles)
                        .mvcMatchers("/restPart/**").hasAnyRole(roles)
                        .mvcMatchers("/").permitAll()
                        .mvcMatchers("/create-user").permitAll()
                                    .and()
                                    .formLogin()
                                    .loginPage("/login").permitAll();

        http.headers().httpStrictTransportSecurity() //pt invoice dll dar nu merge
                      .disable();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public ConfigurableServletWebServerFactory webServerFactory() {
//        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "|{}[]"));
//        return factory;
//    }
}
