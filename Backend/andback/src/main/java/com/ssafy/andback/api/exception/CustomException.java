package com.ssafy.andback.api.exception;

import com.ssafy.andback.api.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
*
* CustomException
*
* @author hoony
* @version 1.0.0
* 생성일 2022-04-28
* 마지막 수정일 2022-04-28
**/

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
