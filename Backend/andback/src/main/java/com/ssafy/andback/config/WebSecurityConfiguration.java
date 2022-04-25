package com.ssafy.andback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * WebSecurityConfiguration
 * Spring Security 설정을 위한 Config 파일
 *
 * @author 김영훈
 * @version 1.0.0
 * 생성일 2022-04-18
 * 마지막 수정일 2022-04-18
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //Swagger 허용
                .antMatchers("/swagger-ui/index.html").permitAll()
                .antMatchers("/foodingredient/**").permitAll()
                .antMatchers("/refrigerator/**").permitAll()
                .antMatchers("/user/**").permitAll();
        http.csrf().disable();
    }
}
