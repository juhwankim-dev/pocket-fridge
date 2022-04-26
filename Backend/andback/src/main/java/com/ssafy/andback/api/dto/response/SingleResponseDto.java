package com.ssafy.andback.api.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
*
* SingleResponseDto
* 하나의 데이터만 응답하는 response
*
* @author 김다은
* @version 1.0.0
* 생성일 2022-04-26
* 마지막 수정일 2022-04-26
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
