package com.ssafy.andback.api.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
/**
*
* FindUserPasswordRequestDto
* 비밀번호 찾기를 위한 유저 이메일 request DTO
*
* @author 김다은
* @version 1.0.0
* 생성일 2022-05-03
* 마지막 수정일 2022-05-03
**/
@Getter
public class FindUserPasswordRequestDto {

    @ApiParam(value = "유저 이메일", required = true)
    String userEmail;

    @ApiParam(value = "유저 이름", required = true)
    String userName;
}
