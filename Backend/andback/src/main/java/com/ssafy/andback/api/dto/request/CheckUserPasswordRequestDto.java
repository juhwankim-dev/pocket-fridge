package com.ssafy.andback.api.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
/**
*
* CheckUserPasswordRequestDto
* 회원 정보 수정 전 비밀번호 확인을 위한 request DTO
*
* @author 김다은
* @version 1.0.0
* 생성일 2022-05-03
* 마지막 수정일 2022-05-03
**/
@Getter
public class CheckUserPasswordRequestDto {
    @ApiParam(value = "유저 비밀번호", required = true, example = "ssafy")
    @NotBlank
    private String userPassword;
}
