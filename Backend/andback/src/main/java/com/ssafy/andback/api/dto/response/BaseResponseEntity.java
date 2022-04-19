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
public class BaseResponseEntity {
    private int status;
    private String code;
    private String message;

    public BaseResponseEntity(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
