package com.ssafy.andback.api.dto.response;

import lombok.Builder;
import lombok.Data;

/**
*
* CheckUserResponseDto
* 회원 정보 조회를 위한 response DTO
*
* @author 김다은
* @version 1.0.0
* 생성일 2022-05-02
* 마지막 수정일 2022-05-02
**/
@Data
public class CheckUserResponseDto {

    private Long userId;

    private String userEmail;

    private String userName;

    private String userNickname;

    private String userPicture;

    @Builder
    public CheckUserResponseDto(Long userId, String userEmail, String userName, String userNickname, String userPicture){
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userPicture = userPicture;
    }
}
