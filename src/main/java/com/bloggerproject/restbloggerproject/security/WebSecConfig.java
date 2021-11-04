package com.bloggerproject.restbloggerproject.security;

import com.bloggerproject.restbloggerproject.appuser.model.AppUserPermission;
import com.bloggerproject.restbloggerproject.appuser.model.AppUserRole;


import com.bloggerproject.restbloggerproject.appuser.service.MyUserService;
import com.bloggerproject.restbloggerproject.appuser.utils.CustomAccessDenied;
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

    CustomAccessDenied accessDeniedHandler;

   @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //antMatchers-> for whitelisting url-s -> no auth needed
                .antMatchers("/" , "index" , "/css/*" , "/js/*", "/register").permitAll() // we need to permit or deny these exceptions
                .antMatchers(HttpMethod.GET, "/users/**", "/blogs/**").hasAuthority(AppUserPermission.READ_ALL.getPermission())
                .antMatchers(HttpMethod.POST,"/template").hasRole("ADMIN")
                .antMatchers("/user/**" ).authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/home", true)
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);

    }



}
