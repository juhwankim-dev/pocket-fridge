package com.ssafy.andback.api.dto;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

/**
*
* TokenDto
* 토큰 DTO
*
* @author hoony
* @version 1.0.0
* 생성일 2022-04-29
* 마지막 수정일 2022-04-29
**/

@Data
@Builder
public class TokenDto {

    @NotNull
    @ApiParam(value = "토큰 저장 고유 번호", required = true)
    private String tokenDeviceNum;

    @NotNull
    @ApiParam(value = "유저 이메일", readOnly = true)
    private String userEmail;

    @NotNull
    @ApiParam(value = "토큰 번호", required = true)
    private String tokenNum;

}
