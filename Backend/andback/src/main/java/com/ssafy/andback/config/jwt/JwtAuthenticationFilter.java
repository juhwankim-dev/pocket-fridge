package com.ssafy.andback.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
*
* JwtAuthenticationFilter
* 유효한 토큰인지 인증하기 위한 파일
*
* @author 김다은
* @version 1.0.0
* 생성일 2022-04-30
* 마지막 수정일 2022-05-02
**/
//@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // Request로 들어오는 Jwt Token의 유효성을 검증(jwtAuthenticationProvider.validateToken) 하는
    // filter를 filterChain에 등록한다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 JWT 받아오기
        String token = jwtAuthenticationProvider.resolveToken((HttpServletRequest) request);  // Header에서 토큰값 가져오기

        System.out.println("token = " + token);

        // 유효한 토큰인지 확인
        if(token != null && jwtAuthenticationProvider.validateToken(token)){
            // 토큰이 유효하면 토큰으로부터 유저 정보 받아오기
            Authentication authentication = jwtAuthenticationProvider.getAuthentication(token);
            System.out.println("authentication = " + authentication);
            // SecurityContext 에 Authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
