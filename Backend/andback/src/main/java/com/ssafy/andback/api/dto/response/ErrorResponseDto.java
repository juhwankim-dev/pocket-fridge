package com.ssafy.andback.api.dto.response;

import com.ssafy.andback.api.constant.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;


/**
*
* ErrorResponse
* ErrorResponseDto
*
* @author hoony
* @version 1.0.0
* 생성일 2022-04-28
* 마지막 수정일 2022-04-28
**/

@Getter
@Builder
public class ErrorResponseDto {
    private final int status;
    private final String message;

    public static ResponseEntity<ErrorResponseDto> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponseDto.builder()
                        .status(errorCode.getHttpStatus().value())
                        .message(errorCode.getMessage())
                        .build()
                );
    }
}
