package com.ssafy.andback.api.dto.response;

import lombok.Getter;
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
public class SingleResponseDto<T> extends BaseResponseDto{
    private T data;

    public SingleResponseDto(int status, String message, T data) {
        super(status, message);
        this.data = data;
    }
}
