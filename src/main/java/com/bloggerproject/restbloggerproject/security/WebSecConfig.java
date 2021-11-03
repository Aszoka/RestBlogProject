package com.bloggerproject.restbloggerproject.security;

import com.bloggerproject.restbloggerproject.appuser.model.AppUserPermission;
import com.bloggerproject.restbloggerproject.appuser.model.AppUserRole;


import com.bloggerproject.restbloggerproject.appuser.service.MyUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor

public class WebSecConfig extends WebSecurityConfigurerAdapter {


   @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //antMatchers-> for whitelisting url-s -> no auth needed
                .antMatchers("/" , "index" , "/css/*" , "/js/*", "/register").permitAll() // we need to permit or deny these exceptions
                .antMatchers(HttpMethod.GET, "/users/**").hasAuthority(AppUserPermission.READ_ALL.getPermission())
                .antMatchers(HttpMethod.POST,"/template").hasRole("ADMIN")
                .antMatchers("/user/**" , "/blogs/**").authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll();

    }



}
