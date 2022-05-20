package com.ssafy.andback.api.dto.response;

import com.ssafy.andback.api.constant.LoginType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class TokenResponseDto {

    // jwt 토큰 값
    private String jwtToken;

    // 로그인 타입 : google, common, kakao
    private LoginType loginType;

    @Builder
    public TokenResponseDto(String jwtToken, LoginType loginType) {
        this.jwtToken = jwtToken;
        this.loginType = loginType;
    }
}
