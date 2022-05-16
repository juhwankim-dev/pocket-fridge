package com.ssafy.andback.api.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefrigeratorShareUserResponseDto {

    // 닉네임
    private String userNickname;

    // 이메일
    private String userEmail;

    // 소유자
    private Boolean owner;

    // 유저 이미지
    private String pictureUrl;


    @QueryProjection
    public RefrigeratorShareUserResponseDto(String userNickname, String userEmail, Boolean owner, String pictureUrl) {
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.owner = owner;
        this.pictureUrl = pictureUrl;
    }
}
