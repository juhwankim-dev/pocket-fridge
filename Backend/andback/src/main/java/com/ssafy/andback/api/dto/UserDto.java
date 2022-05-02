package com.ssafy.andback.api.dto;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDto
 * User Entity 의 데이터 교환을 위해 사용하는 객체
 *
 * @author 김다은
 * @version 1.0.0
 * 생성일 2022-04-19
 * 마지막 수정일 2022-04-25
 **/
@Getter
@Setter
public class UserDto {

    @ApiParam(value = "유저 이메일", required = true, example = "ssafy@ssafy.com")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String userEmail;

    @ApiParam(value = "유저 이름(2~20자)", required = true, example = "김싸피")
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣A-Za-z0-9-_]{2,20}$", message = "이름은 2~20자리여야 합니다.")
    private String userName;

    @ApiParam(value = "유저 닉네임(특수문자 제외 2~10자)", required = true, example = "김싸피")
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣A-Za-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String userNickname;

    @ApiParam(value = "유저 비밀번호(영문자 포함한 5~15자)", required = true, example = "ssafy")
    @NotBlank
    @Pattern(regexp = "(?=.*[a-zA-Z]).{5,15}", message = "비밀번호는 영문자를 포함한 5~15자리여야 합니다.")
    private String userPassword;

    @ApiParam(value = "유저 사진(null 허용)")
    private String userPicture;

//    @ElementCollection(fetch = FetchType.EAGER)
//    private List<String> roles = new ArrayList<>();

}
