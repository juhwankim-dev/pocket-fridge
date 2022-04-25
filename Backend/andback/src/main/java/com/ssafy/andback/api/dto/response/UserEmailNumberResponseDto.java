package com.ssafy.andback.api.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
*
* UserEmailNumberResponseDto
* 이메일 인증번호 response
*
* @author 김다은
* @version 1.0.0
* 생성일 2022-04-25
* 마지막 수정일 2022-04-25
**/

@Getter
@Setter
public class UserEmailNumberResponseDto extends BaseResponseDto{
    private String userEmailNumber;

    public static UserEmailNumberResponseDto of(Integer status, String message, String userEmailNumber) {
        UserEmailNumberResponseDto body = new UserEmailNumberResponseDto();
        body.setMessage(message);
        body.setStatus(status);
        body.userEmailNumber = userEmailNumber;
        return body;
    }
}
