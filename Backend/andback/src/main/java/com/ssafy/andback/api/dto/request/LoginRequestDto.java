package com.ssafy.andback.api.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
*
* LoginRequestDto
* 로그인 request
*
* @author 김다은
* @version 1.0.0
* 생성일 2022-04-25
* 마지막 수정일 2022-04-25
**/

@Getter
@Setter
public class LoginRequestDto {

    @ApiParam(value = "유저 이메일", required = true, example = "ssafy@ssafy.com")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String userEmail;

    @ApiParam(value = "유저 비밀번호(영문자 포함한 5~15자)", required = true, example = "ssafy")
    @NotBlank
    private String userPassword;

}
