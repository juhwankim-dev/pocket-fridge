package com.ssafy.andback.api.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
* BaseResponseEntity
* 기본 ResponseEntity
*
* @author hoony
* @version 1.0.0
* 생성일 2022-04-19
* 마지막 수정일 2022-04-19
**/

@Data
@NoArgsConstructor
public class BaseResponseDto {
    private int status;
    private String message;

    public BaseResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static BaseResponseDto of(Integer status, String message) {
        BaseResponseDto body = new BaseResponseDto();
        body.message = message;
        body.status = status;
        return body;
    }
}
