package com.ssafy.andback.api.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class UpdateUserRequestDto {

    @ApiParam(value = "유저 닉네임(특수문자 제외 2~10자)", required = true, example = "김싸피")
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣A-Za-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String userNickname;

    @ApiParam(value = "유저 비밀번호(영문자 포함한 5~15자), 빈 칸으로 두면 원래 비밀번호로 그대로", required = true, example = "ssafy")
    @Pattern(regexp = "(?=.*[a-zA-Z]).{5,15}", message = "비밀번호는 영문자를 포함한 5~15자리여야 합니다.")
    private String userPassword;

    @ApiParam(value = "유저 사진(null 허용)")
    private String userPicture;
}
