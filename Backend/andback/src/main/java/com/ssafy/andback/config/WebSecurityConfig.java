package com.ssafy.andback.config;

import com.ssafy.andback.config.jwt.JwtAuthenticationFilter;
import com.ssafy.andback.config.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * WebSecurityConfiguration
 * Spring Security 설정을 위한 Config 파일
 *
 * @author 김영훈, 김다은
 * @version 1.0.0
 * 생성일 2022-04-18
 * 마지막 수정일 2022-05-02
 **/
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // 암호화에 필요한 PasswordEncoder 를 Bean 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // authenticationManager 를 Bean 등록
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()    // 요청에 대한 사용권한 체크
                //Swagger 허용
                .antMatchers("/swagger-ui/index.html").permitAll()
                .antMatchers("/foodingredient/**").permitAll()
                .antMatchers("/refrigerator/**").permitAll()
                .antMatchers("/user/**").permitAll()   // "USER" 권한을 가진 유저만 접근 가능
                .antMatchers("/like/**").permitAll();
        http
                .csrf().disable() // csrf 보안 토큰 disable 처리
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 안함
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtAuthenticationProvider),
                        UsernamePasswordAuthenticationFilter.class);
                // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
    }
}
