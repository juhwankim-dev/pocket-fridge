package com.ssafy.andback.api.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
* LoginResponseDto
* 로그인 response
*
* @author 김다은
* @version 1.0.0
* 생성일 2022-04-25
* 마지막 수정일 2022-04-25
**/

@Data
@NoArgsConstructor
public class LoginResponseDto extends BaseResponseDto{
    String jwtToken;

    public static LoginResponseDto of(Integer status, String message, String accessToken) {
        LoginResponseDto body = new LoginResponseDto();
        body.setMessage(message);
        body.setStatus(status);
        body.setJwtToken(accessToken);
        return body;
    }
}
