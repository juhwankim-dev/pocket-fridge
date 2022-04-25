package com.ssafy.andback.api.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String userEmail;

    @ApiParam(value = "유저 비밀번호(영문자 포함한 5~15자)", required = true, example = "ssafy")
    @NotBlank
    @Pattern(regexp = "(?=.*[a-zA-Z]).{5,15}", message = "비밀번호는 영문자를 포함한 5~15자리여야 합니다.")
    private String userPassword;

}
