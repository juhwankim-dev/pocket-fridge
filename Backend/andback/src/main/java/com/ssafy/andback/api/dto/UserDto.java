package com.ssafy.andback.api.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * UserDto
 * User Entity 의 데이터 교환을 위해 사용하는 객체
 *
 * @author 김다은
 * @version 1.0.0
 * 생성일 2022-04-19
 * 마지막 수정일 2022-04-19
 **/
@Getter
@Setter
public class UserDto {
    @NonNull
    private String userEmail;
    @NonNull
    private String userName;
    @NonNull
    private String userNickname;
    @NonNull
    private String userPassword;
    private String userPicture;
}
